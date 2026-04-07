package com.ruoyi.zlm.hook;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.zlm.api.domain.ZlmMediaServer;
import com.ruoyi.zlm.api.hook.OnStreamChangedHookParam;
import com.ruoyi.zlm.api.utils.MediaServerUtils;
import com.ruoyi.zlm.config.UserSetting;
import com.ruoyi.zlm.event.MediaArrivalEvent;
import com.ruoyi.zlm.event.MediaDepartureEvent;
import com.ruoyi.zlm.hook.event.HookZlmServerKeepaliveEvent;
import com.ruoyi.zlm.service.IMediaServerService;
import com.ruoyi.zlm.service.IMediaService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

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

    @Autowired
    private UserSetting userSetting;

    @Autowired
    private IMediaService mediaService;


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
        JSONObject json = (JSONObject) JSON.toJSON(param);

        String mediaServerId = json.getString("mediaServerId");
        ZlmMediaServer mediaServer = mediaServerService.getOne(mediaServerId);
        if (mediaServer == null) {
            HookResultForOnPublish fail = HookResultForOnPublish.Fail();
            log.warn("[ZLM HOOK]推流鉴权 响应：{}->找不到对应的mediaServer", param.getMediaServerId());
            return fail;
        }

        ResultForOnPublish resultForOnPublish = mediaService.authenticatePublish(mediaServer, param.getApp(), param.getStream(), param.getParams());
        if (resultForOnPublish != null) {
            HookResultForOnPublish successResult = HookResultForOnPublish.getInstance(resultForOnPublish);
            log.info("[ZLM HOOK]推流鉴权 响应：{}->{}->>>>{}", param.getMediaServerId(), param, successResult);
            return successResult;
        }else {
            HookResultForOnPublish fail = HookResultForOnPublish.Fail();
            log.info("[ZLM HOOK]推流鉴权 响应：{}->{}->>>>{}", param.getMediaServerId(), param, fail);
            return fail;
        }
    }

    /**
     * rtsp/rtmp流注册或注销时触发此事件；此事件对回复不敏感。
     */
    @ResponseBody
    @PostMapping(value = "/on_stream_changed", produces = "application/json;charset=UTF-8")
    public HookResult onStreamChanged(@RequestBody OnStreamChangedHookParam param) {
        ZlmMediaServer mediaServer = mediaServerService.getOne(param.getMediaServerId());
        if (mediaServer == null) {
            return HookResult.SUCCESS();
        }
        if (!ObjectUtils.isEmpty(mediaServer.getTranscodeSuffix()) && !"null".equalsIgnoreCase(mediaServer.getTranscodeSuffix()) && param.getStream().endsWith(mediaServer.getTranscodeSuffix())) {
            return HookResult.SUCCESS();
        }
        if (param.getSchema().equalsIgnoreCase("rtsp")) {
            if (param.isRegist()) {
                log.info("[ZLM HOOK] 流注册, {}->{}->{}/{}", param.getMediaServerId(), param.getSchema(), param.getApp(), param.getStream());
                String queryParams = param.getParams();
                if (queryParams == null) {
                    try {
                        URL url = new URL("http" + param.getOriginUrl().substring(4));
                        queryParams = url.getQuery();
                    } catch (MalformedURLException ignored) {
                    }
                }
                if (queryParams != null) {
                    param.setParamMap(MediaServerUtils.urlParamToMap(queryParams));
                } else {
                    param.setParamMap(new HashMap<>());
                }
                MediaArrivalEvent mediaArrivalEvent = MediaArrivalEvent.getInstance(this, param, mediaServer, userSetting.getServerId());
                applicationEventPublisher.publishEvent(mediaArrivalEvent);
            } else {
                log.info("[ZLM HOOK] 流注销, {}->{}->{}/{}", param.getMediaServerId(), param.getSchema(), param.getApp(), param.getStream());
                MediaDepartureEvent mediaDepartureEvent = MediaDepartureEvent.getInstance(this, param, mediaServer);
                applicationEventPublisher.publishEvent(mediaDepartureEvent);
            }
        }

        return HookResult.SUCCESS();
    }

    /**
     * 流无人观看时事件，用户可以通过此事件选择是否关闭无人看的流。
     */
    @ResponseBody
    @PostMapping(value = "/on_stream_none_reader", produces = "application/json;charset=UTF-8")
    public JSONObject onStreamNoneReader(@RequestBody OnStreamNoneReaderHookParam param) {
        log.info("[ZLM HOOK]流无人观看：{}->{}->{}/{}", param.getMediaServerId(), param.getSchema(), param.getApp(), param.getStream());

        ZlmMediaServer mediaInfo = mediaServerService.getOne(param.getMediaServerId());
        if (mediaInfo == null) {
            JSONObject ret = new JSONObject();
            ret.put("code", 0);
            return ret;
        }
        if (mediaInfo.getTranscodeSuffix() != null && param.getStream().endsWith(mediaInfo.getTranscodeSuffix())) {
            param.setStream(param.getStream().substring(0, param.getStream().lastIndexOf(mediaInfo.getTranscodeSuffix()) - 1));
        }
        if (!ObjectUtils.isEmpty(mediaInfo.getTranscodeSuffix()) && !"null".equalsIgnoreCase(mediaInfo.getTranscodeSuffix()) && param.getStream().endsWith(mediaInfo.getTranscodeSuffix())) {
            param.setStream(param.getStream().substring(0, param.getStream().lastIndexOf(mediaInfo.getTranscodeSuffix()) - 1));
        }

        JSONObject ret = new JSONObject();
        boolean close = mediaService.closeStreamOnNoneReader(param.getMediaServerId(), param.getApp(), param.getStream(), param.getSchema());
        log.info("[ZLM HOOK]流无人观看是否触发关闭：{}, {}->{}->{}/{}", close, param.getMediaServerId(), param.getSchema(), param.getApp(), param.getStream());
        ret.put("code", 0);
        ret.put("close", close);
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
