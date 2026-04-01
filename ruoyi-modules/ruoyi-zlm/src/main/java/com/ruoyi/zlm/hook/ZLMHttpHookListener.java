package com.ruoyi.zlm.hook;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.zlm.domain.ZlmMediaServer;
import com.ruoyi.zlm.hook.event.HookZlmServerKeepaliveEvent;
import com.ruoyi.zlm.service.IMediaServerService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

/**
 * @description:针对 ZLMediaServer的hook事件监听
 * @author: swwheihei
 * @date: 2020年5月8日 上午10:46:48
 */
@Slf4j
@RestController
@RequestMapping("/index/hook")
@Hidden
public class ZLMHttpHookListener {

    @Autowired
    private IMediaServerService mediaServerService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    /**
     * 服务器定时上报时间，上报间隔可配置，默认10s上报一次
     */
    @ResponseBody
    @PostMapping(value = "/on_server_keepalive", produces = "application/json;charset=UTF-8")
    public HookResult onServerKeepalive(@RequestBody OnServerKeepaliveHookParam param) {
        try {
            HookZlmServerKeepaliveEvent event = new HookZlmServerKeepaliveEvent(this);
            ZlmMediaServer mediaServerItem = mediaServerService.getOne(param.getMediaServerId());
            if (mediaServerItem != null) {
                event.setMediaServerItem(mediaServerItem);
                applicationEventPublisher.publishEvent(event);
            }
        } catch (Exception e) {
            log.info("[ZLM-HOOK-心跳] 发送通知失败 ", e);
        }
        return HookResult.SUCCESS();
    }

    /**
     * 播放器鉴权事件，rtsp/rtmp/http-flv/ws-flv/hls的播放都将触发此鉴权事件。
     */
    @ResponseBody
    @PostMapping(value = "/on_play", produces = "application/json;charset=UTF-8")
    public HookResult onPlay(@RequestBody OnPlayHookParam param) {
        log.info("[ZLM HOOK] 播放鉴权：{}->{}->{}/{}", param.getMediaServerId(), param.getSchema(), param.getApp(), param.getStream());
        return HookResult.SUCCESS();
    }

    /**
     * rtsp/rtmp/rtp推流鉴权事件。
     */
    @ResponseBody
    @PostMapping(value = "/on_publish", produces = "application/json;charset=UTF-8")
    public HookResultForOnPublish onPublish(@RequestBody OnPublishHookParam param) {
        log.info("[ZLM HOOK] 推流鉴权：{}->{}->{}/{}", param.getMediaServerId(), param.getSchema(), param.getApp(), param.getStream());
        return HookResultForOnPublish.Fail();
    }

    /**
     * rtsp/rtmp流注册或注销时触发此事件；此事件对回复不敏感。
     */
    @ResponseBody
    @PostMapping(value = "/on_stream_changed", produces = "application/json;charset=UTF-8")
    public HookResult onStreamChanged(@RequestBody OnStreamChangedHookParam param) {
        log.info("[ZLM HOOK] 流变化：{}->{}->{}/{}", param.getMediaServerId(), param.getSchema(), param.getApp(), param.getStream());
        return HookResult.SUCCESS();
    }

    /**
     * 流无人观看时事件，用户可以通过此事件选择是否关闭无人看的流。
     */
    @ResponseBody
    @PostMapping(value = "/on_stream_none_reader", produces = "application/json;charset=UTF-8")
    public JSONObject onStreamNoneReader(@RequestBody OnStreamNoneReaderHookParam param) {
        log.info("[ZLM HOOK] 流无人观看：{}->{}->{}/{}", param.getMediaServerId(), param.getSchema(), param.getApp(), param.getStream());
        JSONObject ret = new JSONObject();
        ret.put("code", 0);
        return ret;
    }

    /**
     * 流未找到事件，用户可以在此事件触发时，立即去拉流，这样可以实现按需拉流；此事件对回复不敏感。
     */
    @ResponseBody
    @PostMapping(value = "/on_stream_not_found", produces = "application/json;charset=UTF-8")
    public HookResult onStreamNotFound(@RequestBody OnStreamNotFoundHookParam param) {
        log.info("[ZLM HOOK] 流未找到：{}->{}->{}/{}", param.getMediaServerId(), param.getSchema(), param.getApp(), param.getStream());
        return HookResult.SUCCESS();
    }

    /**
     * 服务器启动事件，可以用于监听服务器崩溃重启；此事件对回复不敏感。
     */
    @ResponseBody
    @PostMapping(value = "/on_server_started", produces = "application/json;charset=UTF-8")
    public HookResult onServerStarted(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
        log.info("[ZLM HOOK] 服务器启动：{}->{}", jsonObject.get("mediaServerId"), jsonObject.get("serverStartTime"));
        return HookResult.SUCCESS();
    }

    /**
     * 发送rtp(startSendRtp)被动关闭时回调
     */
    @ResponseBody
    @PostMapping(value = "/on_send_rtp_stopped", produces = "application/json;charset=UTF-8")
    public HookResult onSendRtpStopped(HttpServletRequest request, @RequestBody OnSendRtpStoppedHookParam param) {
        log.info("[ZLM HOOK] 发送rtp(startSendRtp)被动关闭时回调：{}->{}->{}/{}", param.getMediaServerId(), param.getStream(), param.getApp(), param.getStream());
        return HookResult.SUCCESS();
    }

    /**
     * rtpServer收流超时
     */
    @ResponseBody
    @PostMapping(value = "/on_rtp_server_timeout", produces = "application/json;charset=UTF-8")
    public HookResult onRtpServerTimeout(@RequestBody OnRtpServerTimeoutHookParam param) {
        log.info("[ZLM HOOK] rtpServer收流超时：{}->{}", param.getMediaServerId(), param.getStream_id());
        return HookResult.SUCCESS();
    }

    /**
     * 录像完成事件
     */
    @ResponseBody
    @PostMapping(value = "/on_record_mp4", produces = "application/json;charset=UTF-8")
    public HookResult onRecordMp4(HttpServletRequest request, @RequestBody OnRecordMp4HookParam param) {
        log.info("[ZLM HOOK] 录像完成事件：{}->{}->{}/{}", param.getMediaServerId(), param.getStream(), param.getApp(), param.getStream());
        return HookResult.SUCCESS();
    }
}
