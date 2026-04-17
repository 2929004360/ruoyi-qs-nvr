package com.ruoyi.zlm.controller;

import com.alibaba.nacos.api.model.v2.ErrorCode;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.enums.LiveStreamType;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.zlm.api.domain.*;
import com.ruoyi.zlm.common.InviteErrorCode;
import com.ruoyi.zlm.config.UserSetting;
import com.ruoyi.zlm.domain.Snap;
import com.ruoyi.zlm.mediaServer.MediaServerChangeEvent;
import com.ruoyi.zlm.service.ErrorCallback;
import com.ruoyi.zlm.service.IMediaServerService;
import com.ruoyi.zlm.utils.ZLMRESTfulUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * zlm 接口
 *
 * @FileName ZlmController
 * @Description
 * @Author fengcheng
 * @date 2026-04-01
 **/
@Slf4j
@RestController
public class ZlmController {

    @Autowired
    private ZLMRESTfulUtils zlmresTfulUtils;


    @Autowired
    private IMediaServerService mediaServerService;

    @Autowired
    private UserSetting userSetting;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    /**
     * 拉流播放
     *
     * @param streamPullPlay 拉流播放请求参数
     * @param request        HttpServletRequest
     * @return
     */
    @PostMapping("/streamPullPlay")
    public DeferredResult<R<StreamContent>> streamPullPlay(@RequestBody StreamPullPlay streamPullPlay, HttpServletRequest request) {
        log.info("拉流播放代理： app：{}-stream：{}", streamPullPlay.getApp(), streamPullPlay.getStream());
        DeferredResult<R<StreamContent>> result = new DeferredResult<>(userSetting.getPlayTimeout().longValue());

        ErrorCallback<StreamInfo> callback = (code, msg, streamInfo) -> {
            if (code == InviteErrorCode.SUCCESS.getCode()) {
                R<StreamContent> r = R.ok();
                if (streamInfo != null) {
                    if (userSetting.getUseSourceIpAsStreamIp()) {
                        streamInfo = streamInfo.clone();//深拷贝
                        String host;
                        try {
                            URL url = new URL(request.getRequestURL().toString());
                            host = url.getHost();
                        } catch (MalformedURLException e) {
                            host = request.getLocalAddr();
                        }
                        streamInfo.changeStreamIp(host);
                    }
                    if (!ObjectUtils.isEmpty(streamInfo.getMediaServer().getTranscodeSuffix()) && !"null".equalsIgnoreCase(streamInfo.getMediaServer().getTranscodeSuffix())) {
                        streamInfo.setStream(streamInfo.getStream() + "_" + streamInfo.getMediaServer().getTranscodeSuffix());
                    }
                    r.setData(new StreamContent(streamInfo));
                } else {
                    r.setCode(code);
                    r.setMsg(msg);
                }

                result.setResult(r);
            } else {
                result.setResult(R.fail(code, msg));
            }
        };

        mediaServerService.streamPullPlay(streamPullPlay, callback);
        return result;
    }

    /**
     * 停止拉流播放
     *
     * @param streamPullPlay 拉流播放请求参数
     * @return
     */
    @PostMapping("/stopStreamPullPlay")
    public AjaxResult stopStreamPullPlay(@RequestBody StreamPullPlay streamPullPlay) {
        mediaServerService.stopStreamPullPlay(streamPullPlay);
        return AjaxResult.success();
    }

    /**
     * 获取截图
     *
     * @param snap 截图参数
     * @return
     */
    @PostMapping("/getSnap")
    public AjaxResult getSnap(@RequestBody Snap snap) {
        ZlmMediaServer mediaServer = mediaServerService.getMediaServerForMinimumLoad(null);
        String filePath = mediaServerService.getSnap(mediaServer, snap);
        return AjaxResult.success(filePath);
    }

    /**
     * rtp播放
     *
     * @param rtpServerParam 创建rtp端口请求参数
     * @param request        HttpServletRequest
     * @return
     */
    @PostMapping("/rtpPlay")
    public DeferredResult<R<StreamContent>> rtpPlay(@RequestBody RTPServerParam rtpServerParam, HttpServletRequest request) {
        log.info("rtp播放： app：{}-stream：{}", rtpServerParam.getApp(), rtpServerParam.getStreamId());

        if (!(LiveStreamType.HIK_SDK.getCode().equals(rtpServerParam.getType())
                || LiveStreamType.HIK_ISUP.getCode().equals(rtpServerParam.getType())
                || LiveStreamType.DAHUA_SDK.getCode().equals(rtpServerParam.getType())
        )) {
            log.error("不支持的播放类型：{}", rtpServerParam.getType());
            throw new RuntimeException("不支持的播放类型");
        }

        DeferredResult<R<StreamContent>> result = new DeferredResult<>(userSetting.getPlayTimeout().longValue());

        ErrorCallback<StreamInfo> callback = (code, msg, streamInfo) -> {
            if (code == InviteErrorCode.SUCCESS.getCode()) {
                R<StreamContent> r = R.ok();
                if (streamInfo != null) {
                    if (userSetting.getUseSourceIpAsStreamIp()) {
                        streamInfo = streamInfo.clone();//深拷贝
                        String host;
                        try {
                            URL url = new URL(request.getRequestURL().toString());
                            host = url.getHost();
                        } catch (MalformedURLException e) {
                            host = request.getLocalAddr();
                        }
                        streamInfo.changeStreamIp(host);
                    }
                    if (!ObjectUtils.isEmpty(streamInfo.getMediaServer().getTranscodeSuffix()) && !"null".equalsIgnoreCase(streamInfo.getMediaServer().getTranscodeSuffix())) {
                        streamInfo.setStream(streamInfo.getStream() + "_" + streamInfo.getMediaServer().getTranscodeSuffix());
                    }
                    r.setData(new StreamContent(streamInfo));
                } else {
                    r.setCode(code);
                    r.setMsg(msg);
                }

                result.setResult(r);
            } else {
                result.setResult(R.fail(code, msg));
            }
        };

        mediaServerService.rtpPlay(rtpServerParam, callback);
        return result;
    }

    /**
     * 停止rtp播放
     *
     * @param rtpServerParam 创建rtp端口请求参数
     * @return
     */
    @PostMapping("/stopRtpPlay")
    public AjaxResult stopRtpPlay(@RequestBody RTPServerParam rtpServerParam) {
        log.info("停止rtp播放： id：{}", rtpServerParam.getId());
        if (!(LiveStreamType.HIK_SDK.getCode().equals(rtpServerParam.getType())
                || LiveStreamType.HIK_ISUP.getCode().equals(rtpServerParam.getType())
                || LiveStreamType.DAHUA_SDK.getCode().equals(rtpServerParam.getType())
        )) {
            log.error("不支持的播放类型：{}", rtpServerParam.getType());
            throw new RuntimeException("不支持的播放类型");
        }
        mediaServerService.stopRtpPlay(rtpServerParam);
        return AjaxResult.success();
    }

    /**
     * 加载文件形成播放地址
     *
     * @param id 设备id
     * @return
     */
    @GetMapping("/loadRecord/{id}")
    public DeferredResult<R<StreamContent>> loadRecord(@PathVariable Long id, HttpServletRequest request) {
        DeferredResult<R<StreamContent>> result = new DeferredResult<>();

        result.onTimeout(() -> {
            log.info("[加载录像文件超时] id={}", id);
            R<StreamContent> wvpResult = R.fail();
            wvpResult.setMsg("加载录像文件超时");
            result.setResult(wvpResult);
        });

        ErrorCallback<StreamInfo> callback = (code, msg, streamInfo) -> {

            R<StreamContent> wvpResult = new R<>();
            if (code == InviteErrorCode.SUCCESS.getCode()) {
                wvpResult.setCode(ErrorCode.SUCCESS.getCode());
                wvpResult.setMsg(ErrorCode.SUCCESS.getMsg());

                if (streamInfo != null) {
                    if (userSetting.getUseSourceIpAsStreamIp()) {
                        streamInfo = streamInfo.clone();//深拷贝
                        String host;
                        try {
                            URL url = new URL(request.getRequestURL().toString());
                            host = url.getHost();
                        } catch (MalformedURLException e) {
                            host = request.getLocalAddr();
                        }
                        streamInfo.changeStreamIp(host);
                    }
                    if (!org.springframework.util.ObjectUtils.isEmpty(streamInfo.getMediaServer().getTranscodeSuffix()) && !"null".equalsIgnoreCase(streamInfo.getMediaServer().getTranscodeSuffix())) {
                        streamInfo.setStream(streamInfo.getStream() + "_" + streamInfo.getMediaServer().getTranscodeSuffix());
                    }
                    wvpResult.setData(new StreamContent(streamInfo));
                } else {
                    wvpResult.setCode(code);
                    wvpResult.setMsg(msg);
                }
            } else {
                wvpResult.setCode(code);
                wvpResult.setMsg(msg);
            }
            result.setResult(wvpResult);
        };

        mediaServerService.loadRecord(id, callback);
        return result;
    }

    /**
     * 关闭流文件形成播放地址
     *
     * @param id 加载文件参数
     * @return
     */
    @GetMapping("/closeStreams/{id}")
    public AjaxResult closeStreams(@PathVariable Long id) {
        mediaServerService.closeStreams(id);
        return AjaxResult.success();
    }

    /**
     * 获取流媒体服务器列表
     *
     * @return
     */
    @GetMapping(value = "/list")
    public AjaxResult getMediaServerList() {
        List<ZlmMediaServer> list = mediaServerService.getAll();
        return AjaxResult.success(list);
    }

    /**
     * 移除流媒体服务
     *
     * @param id 流媒体ID
     */
    @DeleteMapping(value = "/delete")
    public AjaxResult deleteMediaServer(@RequestParam String id) {
        ZlmMediaServer mediaServer = mediaServerService.getOne(id);
        if (mediaServer == null) {
            throw new RuntimeException("流媒体不存在");
        }
        mediaServerService.delete(mediaServer);
        return AjaxResult.success();
    }

    /**
     * 保存流媒体服务
     *
     * @param mediaServer 流媒体信息
     */
    @PostMapping(value = "/save")
    public AjaxResult saveMediaServer(@RequestBody ZlmMediaServer mediaServer) {
        ZlmMediaServer mediaServerItemInDatabase = mediaServerService.getOneFromDatabase(mediaServer.getId());

        if (mediaServerItemInDatabase != null) {
            mediaServerService.update(mediaServer);
        } else {
            mediaServerService.add(mediaServer);
            // 发送事件
            MediaServerChangeEvent event = new MediaServerChangeEvent(this);
            event.setMediaServerItemList(mediaServer);
            applicationEventPublisher.publishEvent(event);
        }

        return AjaxResult.success();
    }

    /**
     * 测试流媒体服务
     *
     * @param ip     流媒体服务IP
     * @param port   流媒体服务HTT端口
     * @param secret 流媒体服务secret
     * @param type   流媒体服务类型
     * @return
     */
    @GetMapping(value = "/check")
    public AjaxResult checkMediaServer(@RequestParam String ip, @RequestParam int port, @RequestParam String secret, @RequestParam String type) {
        ZlmMediaServer mediaServer = mediaServerService.checkMediaServer(ip, port, secret, type);
        return AjaxResult.success(mediaServer);
    }

    /**
     * 获取流媒体服务
     *
     * @param id 流媒体服务ID
     * @return
     */
    @GetMapping(value = "/one/{id}")
    public AjaxResult getMediaServer(@PathVariable String id) {
        ZlmMediaServer mediaServer = mediaServerService.getOne(id);
        return AjaxResult.success(mediaServer);
    }

    /**
     * 获取流信息
     *
     * @param app           应用名
     * @param stream        流ID
     * @param mediaServerId 流媒体ID
     * @return
     */
    @GetMapping(value = "/media_info")
    public AjaxResult getMediaInfo(String app, String stream, String mediaServerId) {
        ZlmMediaServer mediaServer = mediaServerService.getOne(mediaServerId);
        if (mediaServer == null) {
            throw new RuntimeException("流媒体不存在");
        }
        return AjaxResult.success(mediaServerService.getMediaInfo(mediaServer, app, stream));
    }

    /**
     * 重启流媒体
     *
     * @param mediaServerId 流媒体ID
     * @return
     */
    @GetMapping(value = "/restartServer/{mediaServerId}")
    public AjaxResult restartServer(@PathVariable String mediaServerId) {
        ZlmMediaServer mediaServer = mediaServerService.getOne(mediaServerId);
        if (mediaServer == null) {
            throw new RuntimeException("流媒体不存在");
        }
        mediaServerService.restartServer(mediaServer);
        return AjaxResult.success();
    }

    /**
     * 获取所有在线媒体服务器
     *
     * @return
     */
    @GetMapping(value = "/getAllOnlineMediaServe")
    public AjaxResult getAllOnlineMediaServe() {
        return AjaxResult.success(mediaServerService.getAllOnlineMediaServe());
    }

    /**
     * 生成推流地址
     *
     * @return
     */
    @GetMapping(value = "/getStreamPushAddress/{id}")
    public AjaxResult getStreamPushAddress(@PathVariable Long id, String callId) {
        if (StringUtils.isEmpty(callId)) {
            return AjaxResult.error("callId不能是空");
        }
        return AjaxResult.success(mediaServerService.getStreamPushAddress(id, callId));
    }

    /**
     * 推流播放
     *
     * @param request
     * @param id
     * @return
     */
    @GetMapping(value = "/streamPullPush")
    public DeferredResult<R<StreamContent>> streamPullPush(HttpServletRequest request, Long id) {
        Assert.notNull(id, "设备ID不可为NULL");
        DeferredResult<R<StreamContent>> result = new DeferredResult<>(userSetting.getPlayTimeout().longValue());
        result.onTimeout(() -> {
            R<StreamContent> fail = R.fail("等待推流超时");
            result.setResult(fail);
        });

        mediaServerService.streamPullPush(id, (code, msg, streamInfo) -> {
            if (code == 0 && streamInfo != null) {
                if (userSetting.getUseSourceIpAsStreamIp()) {
                    streamInfo = streamInfo.clone();//深拷贝
                    String host;
                    try {
                        URL url = new URL(request.getRequestURL().toString());
                        host = url.getHost();
                    } catch (MalformedURLException e) {
                        host = request.getLocalAddr();
                    }
                    streamInfo.changeStreamIp(host);
                }
                R<StreamContent> success = R.ok(new StreamContent(streamInfo));
                result.setResult(success);
            }
        });
        return result;
    }
}
