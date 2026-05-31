package com.ruoyi.zlm.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.enums.LiveStreamType;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.gb28181.api.RemoteGb28181Service;
import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.gb28181.api.domain.DeviceChannel;
import com.ruoyi.jt1078.api.RemoteJt1078Service;
import com.ruoyi.jt1078.api.domain.Jt1078Device;
import com.ruoyi.onvif.api.RemoteOnvifService;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.zlm.api.domain.*;
import com.ruoyi.zlm.common.InviteErrorCode;
import com.ruoyi.zlm.common.InviteSessionType;
import com.ruoyi.zlm.config.UserSetting;
import com.ruoyi.zlm.domain.Snap;
import com.ruoyi.zlm.mediaServer.MediaServerChangeEvent;
import com.ruoyi.zlm.service.ErrorCallback;
import com.ruoyi.zlm.service.IInviteStreamService;
import com.ruoyi.zlm.service.IMediaServerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
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
    private IMediaServerService mediaServerService;

    @Autowired
    private UserSetting userSetting;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private RemoteGb28181Service remoteGb28181Service;

    @Autowired
    private RemoteJt1078Service remoteJt1078Service;

    @Autowired
    private RemoteQsDeviceService remoteQsDeviceService;

    @Autowired
    private RemoteOnvifService remoteOnvifService;

    @Autowired
    @Lazy
    private IInviteStreamService inviteStreamService;

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
     * ONVIF回放拉流播放
     *
     * @param onvifPlayback ONVIF回放拉流播放请求参数
     * @param request       HttpServletRequest
     * @return
     */
    @PostMapping("/onvifPlayback")
    public DeferredResult<R<StreamContent>> onvifPlayback(@RequestBody OnvifPlayback onvifPlayback, HttpServletRequest request) {
        log.info("ONVIF回放拉流播放： app：{}-stream：{}-deviceIp：{}", onvifPlayback.getApp(), onvifPlayback.getStream(), onvifPlayback.getDeviceIp());
        DeferredResult<R<StreamContent>> result = new DeferredResult<>(userSetting.getPlayTimeout().longValue());

        // 获取ONVIF回放地址
        R<String> replayUriResult = remoteOnvifService.getReplayUri(
                onvifPlayback.getDeviceIp(),
                onvifPlayback.getUsername(),
                onvifPlayback.getPassword(),
                onvifPlayback.getRecordingToken(),
                onvifPlayback.getTrackToken(),
                SecurityConstants.INNER
        );

        if (replayUriResult.getCode() != Constants.SUCCESS || StringUtils.isEmpty(replayUriResult.getData())) {
            result.setResult(R.fail("获取ONVIF回放地址失败"));
            return result;
        }

        // 构造拉流播放参数
        StreamPullPlay streamPullPlay = new StreamPullPlay();
        streamPullPlay.setDeviceId(onvifPlayback.getDeviceId());
        streamPullPlay.setApp(onvifPlayback.getApp());
        streamPullPlay.setStream(onvifPlayback.getStream());
        streamPullPlay.setUrl(replayUriResult.getData());
        streamPullPlay.setEnable_audio(onvifPlayback.isEnable_audio());
        streamPullPlay.setEnable_mp4(false); // 回放不开启云端录像
        streamPullPlay.setRtp_type(onvifPlayback.getRtp_type());
        streamPullPlay.setTimeOut(onvifPlayback.getTimeOut());
        streamPullPlay.setPlayback(true); // ONVIF回放流，设置为回放类型

        // 回调处理
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
        if (mediaServer == null) {
            throw new RuntimeException("无可用的流媒体服务器");
        }
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

        result.onTimeout(() -> {
            log.info("[rtp播放等待超时] app：{}, stream：{}", rtpServerParam.getApp(), rtpServerParam.getStreamId());
            R<StreamContent> wvpResult = R.fail();
            wvpResult.setMsg("rtp播放超时");
            result.setResult(wvpResult);

            inviteStreamService.removeInviteInfoByDeviceAndChannel(InviteSessionType.PLAY, rtpServerParam.getId());
            mediaServerService.stopRtpPlay(rtpServerParam);
        });

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
     * 设备录像回放（支持海康SDK、海康ISUP、大华SDK）
     *
     * @param rtpServerParam 创建rtp端口请求参数
     * @param request        HttpServletRequest
     * @return
     */
    @PostMapping("/rtpPlayback")
    public DeferredResult<R<StreamContent>> rtpPlayback(@RequestBody RTPServerParam rtpServerParam, HttpServletRequest request) {
        log.info("设备录像回放：类型={}, app={}, streamId={}", rtpServerParam.getType(), rtpServerParam.getApp(), rtpServerParam.getStreamId());

        // 验证支持的类型
        if (!(LiveStreamType.HIK_SDK.getCode().equals(rtpServerParam.getType())
                || LiveStreamType.HIK_ISUP.getCode().equals(rtpServerParam.getType())
                || LiveStreamType.DAHUA_SDK.getCode().equals(rtpServerParam.getType()))) {
            log.error("不支持的回放类型：{}", rtpServerParam.getType());
            throw new RuntimeException("不支持的回放类型，仅支持海康SDK/海康ISUP/大华设备");
        }

        // 标记为回放
        rtpServerParam.setPlayback(true);

        DeferredResult<R<StreamContent>> result = new DeferredResult<>(userSetting.getPlayTimeout().longValue());

        String typeDesc;
        if (LiveStreamType.HIK_SDK.getCode().equals(rtpServerParam.getType())) {
            typeDesc = "海康SDK";
        } else if (LiveStreamType.HIK_ISUP.getCode().equals(rtpServerParam.getType())) {
            typeDesc = "海康ISUP";
        } else {
            typeDesc = "大华SDK";
        }

        result.onTimeout(() -> {
            log.info("[设备录像回放等待超时] type={}, app={}, streamId={}", typeDesc, rtpServerParam.getApp(), rtpServerParam.getStreamId());
            R<StreamContent> wvpResult = R.fail();
            wvpResult.setMsg(typeDesc + "录像回放超时");
            result.setResult(wvpResult);

            inviteStreamService.removeInviteInfoByDeviceAndChannel(InviteSessionType.PLAYBACK, rtpServerParam.getId());
            mediaServerService.stopRtpPlay(rtpServerParam);
        });

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
     * 停止设备录像回放（支持海康SDK、海康ISUP、大华SDK）
     *
     * @param rtpServerParam 创建rtp端口请求参数
     * @return
     */
    @PostMapping("/stopRtpPlayback")
    public AjaxResult stopRtpPlayback(@RequestBody RTPServerParam rtpServerParam) {
        log.info("停止设备录像回放：type={}, id={}", rtpServerParam.getType(), rtpServerParam.getId());
        // 验证支持的类型
        if (!(LiveStreamType.HIK_SDK.getCode().equals(rtpServerParam.getType())
                || LiveStreamType.HIK_ISUP.getCode().equals(rtpServerParam.getType())
                || LiveStreamType.DAHUA_SDK.getCode().equals(rtpServerParam.getType()))) {
            log.error("不支持的回放类型：{}", rtpServerParam.getType());
            throw new RuntimeException("不支持的回放类型，仅支持海康SDK/海康ISUP/大华设备");
        }
        // 标记为回放
        rtpServerParam.setPlayback(true);
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
        DeferredResult<R<StreamContent>> result = new DeferredResult<>(userSetting.getPlayTimeout().longValue());

        result.onTimeout(() -> {
            log.info("[加载录像文件超时] id={}", id);
            R<StreamContent> wvpResult = R.fail();
            wvpResult.setMsg("加载录像文件超时");
            result.setResult(wvpResult);
        });

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
    public AjaxResult getMediaInfo(@RequestParam String app, @RequestParam String stream, @RequestParam String mediaServerId) {
        Assert.hasText(app, "app参数不能为空");
        Assert.hasText(stream, "stream参数不能为空");
        Assert.hasText(mediaServerId, "mediaServerId参数不能为空");
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
    public DeferredResult<R<StreamContent>> streamPullPush(HttpServletRequest request, @RequestParam Long id) {
        Assert.notNull(id, "设备ID不可为NULL");
        DeferredResult<R<StreamContent>> result = new DeferredResult<>(userSetting.getPlayTimeout().longValue());
        result.onTimeout(() -> {
            log.info("[等待推流超时] id={}", id);
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
            } else {
                // 处理失败情况
                log.info("[等待推流失败] id={}, code={}, msg={}", id, code, msg);
                R<StreamContent> fail = R.fail(code, msg);
                result.setResult(fail);
            }
        });
        return result;
    }

    /**
     * gb28181 播放
     *
     * @param request
     * @param id      设备id
     * @return
     */
    @GetMapping("/startGb28181Play/{id}")
    public DeferredResult<R<StreamContent>> startGb28181Play(
            HttpServletRequest request,
            @PathVariable Long id
    ) {
        log.info("[gb28181 开始点播] id：{} ", id);
        Assert.notNull(id, "设备id");

        R<QsDevice> qsDevicer = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (qsDevicer.getCode() != Constants.SUCCESS) {
            throw new RuntimeException("获取设备信息失败 id:" + id);
        }
        Assert.notNull(qsDevicer.getData(), "设备不存在 id:" + id);

        QsDevice qsDevice = qsDevicer.getData();

        if ("OFFLINE".equals(qsDevice.getDeviceStatus())) {
            throw new RuntimeException("设备不在线 id:" + id);
        }

        R<Device> deviceR = remoteGb28181Service.getDeviceByDeviceId(qsDevice.getGbDeviceId(), SecurityConstants.INNER);
        if (deviceR.getCode() != Constants.SUCCESS) {
            throw new RuntimeException("gb28181 获取设备信息失败 id:" + qsDevice.getGbDeviceId());
        }
        Assert.notNull(deviceR.getData(), "gb28181 国标设备不存在 id:" + qsDevice.getGbDeviceId());

        if (!deviceR.getData().isOnLine()) {
            throw new RuntimeException("gb28181 国标设备不在线失败 id:" + qsDevice.getGbDeviceId());
        }

        R<DeviceChannel> deviceChannelR = remoteGb28181Service.getDeviceChannelByChannelId(qsDevice.getGbDeviceId(), qsDevice.getGbChannelId(), SecurityConstants.INNER);
        if (deviceChannelR.getCode() != Constants.SUCCESS) {
            throw new RuntimeException("gb28181 获取设备通道失败 gbDeviceId:" + qsDevice.getGbDeviceId() + "，gbChannelId:" + qsDevice.getGbChannelId());
        }

        Assert.notNull(deviceChannelR.getData(), "gb28181 获取设备通道失败不存在 gbDeviceId:" + qsDevice.getGbDeviceId() + "，gbChannelId:" + qsDevice.getGbChannelId());

        if (!"ON".equals(deviceChannelR.getData().getStatus())) {
            throw new RuntimeException("gb28181 国标设备通道不在线失败 gbDeviceId:" + qsDevice.getGbDeviceId() + "，gbChannelId:" + qsDevice.getGbChannelId());
        }

        DeferredResult<R<StreamContent>> result = new DeferredResult<>(userSetting.getPlayTimeout().longValue());

        result.onTimeout(() -> {
            log.info("[点播等待超时] gbDeviceId：{}, gbChannelId：{}, ", qsDevice.getGbDeviceId(), qsDevice.getGbChannelId());
            // 释放rtpserver
            R<StreamContent> wvpResult = R.fail();
            wvpResult.setMsg("点播超时");
            result.setResult(wvpResult);

            inviteStreamService.removeInviteInfoByDeviceAndChannel(InviteSessionType.PLAY, qsDevice.getId());
            mediaServerService.stopGb28181Play(InviteSessionType.PLAY, qsDevice, deviceR.getData(), qsDevice.getDeviceCode());
        });

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

        qsDevice.setStreamMode(deviceR.getData().getStreamMode());
        mediaServerService.startGb28181Play(qsDevice, deviceR.getData(), callback);
        return result;
    }

    /**
     * gb28181 回放
     *
     * @param request
     * @param id        设备id
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    @GetMapping("/startGb28181Playback/{id}")
    public DeferredResult<R<StreamContent>> startGb28181Playback(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestParam String startTime,
            @RequestParam String endTime
    ) {
        log.info("[gb28181 开始回放] id={}, startTime={}, endTime={}", id, startTime, endTime);
        Assert.notNull(id, "设备id");
        Assert.notNull(startTime, "开始时间");
        Assert.notNull(endTime, "结束时间");

        R<QsDevice> qsDevicer = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (qsDevicer.getCode() != Constants.SUCCESS) {
            throw new RuntimeException("获取设备信息失败 id:" + id);
        }
        Assert.notNull(qsDevicer.getData(), "设备不存在 id:" + id);

        QsDevice qsDevice = qsDevicer.getData();

        if ("OFFLINE".equals(qsDevice.getDeviceStatus())) {
            throw new RuntimeException("设备不在线 id:" + id);
        }

        R<Device> deviceR = remoteGb28181Service.getDeviceByDeviceId(qsDevice.getGbDeviceId(), SecurityConstants.INNER);
        if (deviceR.getCode() != Constants.SUCCESS) {
            throw new RuntimeException("gb28181 获取设备信息失败 id:" + qsDevice.getGbDeviceId());
        }
        Assert.notNull(deviceR.getData(), "gb28181 国标设备不存在 id:" + qsDevice.getGbDeviceId());

        if (!deviceR.getData().isOnLine()) {
            throw new RuntimeException("gb28181 国标设备不在线失败 id:" + qsDevice.getGbDeviceId());
        }

        R<DeviceChannel> deviceChannelR = remoteGb28181Service.getDeviceChannelByChannelId(qsDevice.getGbDeviceId(), qsDevice.getGbChannelId(), SecurityConstants.INNER);
        if (deviceChannelR.getCode() != Constants.SUCCESS) {
            throw new RuntimeException("gb28181 获取设备通道失败 gbDeviceId:" + qsDevice.getGbDeviceId() + "，gbChannelId:" + qsDevice.getGbChannelId());
        }

        Assert.notNull(deviceChannelR.getData(), "gb28181 获取设备通道失败不存在 gbDeviceId:" + qsDevice.getGbDeviceId() + "，gbChannelId:" + qsDevice.getGbChannelId());

        if (!"ON".equals(deviceChannelR.getData().getStatus())) {
            throw new RuntimeException("gb28181 国标设备通道不在线失败 gbDeviceId:" + qsDevice.getGbDeviceId() + "，gbChannelId:" + qsDevice.getGbChannelId());
        }

        DeferredResult<R<StreamContent>> result = new DeferredResult<>(userSetting.getPlayTimeout().longValue());

        result.onTimeout(() -> {
            log.info("[回放等待超时] gbDeviceId={}, gbChannelId={}", qsDevice.getGbDeviceId(), qsDevice.getGbChannelId());
            R<StreamContent> wvpResult = R.fail();
            wvpResult.setMsg("回放超时");
            result.setResult(wvpResult);

            inviteStreamService.removeInviteInfoByDeviceAndChannel(InviteSessionType.PLAYBACK, qsDevice.getId());
            mediaServerService.stopGb28181Play(InviteSessionType.PLAYBACK, qsDevice, deviceR.getData(), qsDevice.getDeviceCode() + "_playback");
        });

        ErrorCallback<StreamInfo> callback = (code, msg, streamInfo) -> {
            if (code == InviteErrorCode.SUCCESS.getCode()) {
                R<StreamContent> r = R.ok();
                if (streamInfo != null) {
                    if (userSetting.getUseSourceIpAsStreamIp()) {
                        streamInfo = streamInfo.clone();
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

        qsDevice.setStreamMode(deviceR.getData().getStreamMode());
        mediaServerService.startGb28181Playback(qsDevice, deviceR.getData(), startTime, endTime, callback);
        return result;
    }

    /**
     * gb28181 停止点播
     *
     * @param id 设备id
     * @return
     */
    @GetMapping("/stopGb28181Play/{id}")
    public AjaxResult stopGb28181Play(@PathVariable Long id) {

        log.info("[gb28181 停止点播] id：{} ", id);
        Assert.notNull(id, "设备id");

        R<QsDevice> qsDevicer = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (qsDevicer.getCode() != Constants.SUCCESS) {
            throw new RuntimeException("获取设备信息失败 id:" + id);
        }
        Assert.notNull(qsDevicer.getData(), "设备不存在 id:" + id);

        QsDevice qsDevice = qsDevicer.getData();

        if ("OFFLINE".equals(qsDevice.getDeviceStatus())) {
            throw new RuntimeException("设备不在线 id:" + id);
        }

        R<Device> deviceR = remoteGb28181Service.getDeviceByDeviceId(qsDevice.getGbDeviceId(), SecurityConstants.INNER);
        if (deviceR.getCode() != Constants.SUCCESS) {
            throw new RuntimeException("gb28181 获取设备信息失败 id:" + qsDevice.getGbDeviceId());
        }
        Assert.notNull(deviceR.getData(), "gb28181 国标设备不存在 id:" + qsDevice.getGbDeviceId());

        if (!deviceR.getData().isOnLine()) {
            throw new RuntimeException("gb28181 国标设备不在线失败 id:" + qsDevice.getGbDeviceId());
        }

        R<DeviceChannel> deviceChannelR = remoteGb28181Service.getDeviceChannelByChannelId(qsDevice.getGbDeviceId(), qsDevice.getGbChannelId(), SecurityConstants.INNER);
        if (deviceChannelR.getCode() != Constants.SUCCESS) {
            throw new RuntimeException("gb28181 获取设备通道失败 gbDeviceId:" + qsDevice.getGbDeviceId() + "，gbChannelId:" + qsDevice.getGbChannelId());
        }

        Assert.notNull(deviceChannelR.getData(), "gb28181 获取设备通道失败不存在 gbDeviceId:" + qsDevice.getGbDeviceId() + "，gbChannelId:" + qsDevice.getGbChannelId());

        if (!"ON".equals(deviceChannelR.getData().getStatus())) {
            throw new RuntimeException("gb28181 国标设备通道不在线失败 gbDeviceId:" + qsDevice.getGbDeviceId() + "，gbChannelId:" + qsDevice.getGbChannelId());
        }

        mediaServerService.stopGb28181Play(InviteSessionType.PLAY, qsDevice, deviceR.getData(), qsDevice.getDeviceCode());
        JSONObject json = new JSONObject();
        json.put("deviceId", qsDevice.getGbDeviceId());
        json.put("channelId", qsDevice.getGbChannelId());
        return AjaxResult.success(json);
    }

    /**
     * gb28181 停止回放
     *
     * @param id 设备id
     * @return
     */
    @GetMapping("/stopGb28181Playback/{id}")
    public AjaxResult stopGb28181Playback(@PathVariable Long id) {

        log.info("[gb28181 停止回放] id={}", id);
        Assert.notNull(id, "设备id");

        R<QsDevice> qsDevicer = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (qsDevicer.getCode() != Constants.SUCCESS) {
            throw new RuntimeException("获取设备信息失败 id:" + id);
        }
        Assert.notNull(qsDevicer.getData(), "设备不存在 id:" + id);

        QsDevice qsDevice = qsDevicer.getData();

        if ("OFFLINE".equals(qsDevice.getDeviceStatus())) {
            throw new RuntimeException("设备不在线 id:" + id);
        }

        R<Device> deviceR = remoteGb28181Service.getDeviceByDeviceId(qsDevice.getGbDeviceId(), SecurityConstants.INNER);
        if (deviceR.getCode() != Constants.SUCCESS) {
            throw new RuntimeException("gb28181 获取设备信息失败 id:" + qsDevice.getGbDeviceId());
        }
        Assert.notNull(deviceR.getData(), "gb28181 国标设备不存在 id:" + qsDevice.getGbDeviceId());

        if (!deviceR.getData().isOnLine()) {
            throw new RuntimeException("gb28181 国标设备不在线失败 id:" + qsDevice.getGbDeviceId());
        }

        R<DeviceChannel> deviceChannelR = remoteGb28181Service.getDeviceChannelByChannelId(qsDevice.getGbDeviceId(), qsDevice.getGbChannelId(), SecurityConstants.INNER);
        if (deviceChannelR.getCode() != Constants.SUCCESS) {
            throw new RuntimeException("gb28181 获取设备通道失败 gbDeviceId:" + qsDevice.getGbDeviceId() + "，gbChannelId:" + qsDevice.getGbChannelId());
        }

        Assert.notNull(deviceChannelR.getData(), "gb28181 获取设备通道失败不存在 gbDeviceId:" + qsDevice.getGbDeviceId() + "，gbChannelId:" + qsDevice.getGbChannelId());

        if (!"ON".equals(deviceChannelR.getData().getStatus())) {
            throw new RuntimeException("gb28181 国标设备通道不在线失败 gbDeviceId:" + qsDevice.getGbDeviceId() + "，gbChannelId:" + qsDevice.getGbChannelId());
        }

        mediaServerService.stopGb28181Play(InviteSessionType.PLAYBACK, qsDevice, deviceR.getData(), qsDevice.getDeviceCode() + "_playback");
        JSONObject json = new JSONObject();
        json.put("deviceId", qsDevice.getGbDeviceId());
        json.put("channelId", qsDevice.getGbChannelId());
        return AjaxResult.success(json);
    }

    /**
     * jt1078 回放
     *
     * @param request
     * @param id 设备id
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    @GetMapping("/startJt1078Playback/{id}")
    public DeferredResult<R<StreamContent>> startJt1078Playback(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestParam String startTime,
            @RequestParam String endTime
    ) {
        log.info("[jt1078 开始回放] id={}, startTime={}, endTime={}", id, startTime, endTime);
        Assert.notNull(id, "设备id");
        Assert.notNull(startTime, "开始时间");
        Assert.notNull(endTime, "结束时间");

        R<QsDevice> qsDevicer = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (qsDevicer.getCode() != Constants.SUCCESS) {
            throw new RuntimeException("获取设备信息失败 id:" + id);
        }
        Assert.notNull(qsDevicer.getData(), "设备不存在 id:" + id);

        QsDevice qsDevice = qsDevicer.getData();

        if ("OFFLINE".equals(qsDevice.getDeviceStatus())) {
            throw new RuntimeException("设备不在线 id:" + id);
        }

        // 通过手机号获取 JT1078 设备信息
        R<Jt1078Device> deviceR = remoteJt1078Service.getDeviceByMobileNo(qsDevice.getJtMobileNo(), SecurityConstants.INNER);
        if (deviceR.getCode() != Constants.SUCCESS) {
            throw new RuntimeException("jt1078 获取设备信息失败 mobileNo:" + qsDevice.getJtMobileNo());
        }
        Assert.notNull(deviceR.getData(), "jt1078 设备不存在 mobileNo:" + qsDevice.getJtMobileNo());

        if (!deviceR.getData().getOnline()) {
            throw new RuntimeException("jt1078 设备不在线 mobileNo:" + qsDevice.getJtMobileNo());
        }

        DeferredResult<R<StreamContent>> result = new DeferredResult<>(userSetting.getPlayTimeout().longValue());

        result.onTimeout(() -> {
            log.info("[jt1078 回放等待超时] deviceId：{}", id);
            // 释放rtpserver
            R<StreamContent> wvpResult = R.fail();
            wvpResult.setMsg("回放超时");
            result.setResult(wvpResult);

            inviteStreamService.removeInviteInfoByDeviceAndChannel(InviteSessionType.PLAYBACK, qsDevice.getId());
            mediaServerService.stopJt1078Play(InviteSessionType.PLAYBACK, qsDevice, deviceR.getData(), qsDevice.getJtMobileNo() + "_playback");
        });

        ErrorCallback<StreamInfo> callback = (code, msg, streamInfo) -> {
            if (code == InviteErrorCode.SUCCESS.getCode()) {
                R<StreamContent> r = R.ok();
                if (streamInfo != null) {
                    if (userSetting.getUseSourceIpAsStreamIp()) {
                        streamInfo = streamInfo.clone(); // 深拷贝
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

        mediaServerService.startJt1078Playback(qsDevice, deviceR.getData(), startTime, endTime, callback);
        return result;
    }

    /**
     * jt1078 停止回放
     *
     * @param id 设备id
     * @return
     */
    @GetMapping("/stopJt1078Playback/{id}")
    public AjaxResult stopJt1078Playback(@PathVariable Long id) {
        log.info("[jt1078 停止回放] id={}", id);
        Assert.notNull(id, "设备id");

        R<QsDevice> qsDevicer = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (qsDevicer.getCode() != Constants.SUCCESS) {
            throw new RuntimeException("获取设备信息失败 id:" + id);
        }
        Assert.notNull(qsDevicer.getData(), "设备不存在 id:" + id);

        QsDevice qsDevice = qsDevicer.getData();

        if ("OFFLINE".equals(qsDevice.getDeviceStatus())) {
            throw new RuntimeException("设备不在线 id:" + id);
        }

        // 通过手机号获取 JT1078 设备信息
        R<Jt1078Device> deviceR = remoteJt1078Service.getDeviceByMobileNo(qsDevice.getJtMobileNo(), SecurityConstants.INNER);
        if (deviceR.getCode() != Constants.SUCCESS) {
            throw new RuntimeException("jt1078 获取设备信息失败 mobileNo:" + qsDevice.getJtMobileNo());
        }
        Assert.notNull(deviceR.getData(), "jt1078 设备不存在 mobileNo:" + qsDevice.getJtMobileNo());

        if (!deviceR.getData().getOnline()) {
            throw new RuntimeException("jt1078 设备不在线 mobileNo:" + qsDevice.getJtMobileNo());
        }

        mediaServerService.stopJt1078Play(InviteSessionType.PLAYBACK, qsDevice, deviceR.getData(), qsDevice.getDeviceCode() + "_playback");
        JSONObject json = new JSONObject();
        json.put("deviceId", id);
        json.put("jtMobileNo", qsDevice.getJtMobileNo());
        return AjaxResult.success(json);
    }

    /**
     * jt1078 播放
     *
     * @param request
     * @param id 设备id
     * @return
     */
    @GetMapping("/startJt1078Play/{id}")
    public DeferredResult<R<StreamContent>> startJt1078Play(
            HttpServletRequest request,
            @PathVariable Long id
    ) {
        log.info("[jt1078 开始点播] id：{} ", id);
        Assert.notNull(id, "设备id");

        R<QsDevice> qsDevicer = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (qsDevicer.getCode() != Constants.SUCCESS) {
            throw new RuntimeException("获取设备信息失败 id：" + id);
        }
        Assert.notNull(qsDevicer.getData(), "设备不存在 id：" + id);

        QsDevice qsDevice = qsDevicer.getData();

        if ("OFFLINE".equals(qsDevice.getDeviceStatus())) {
            throw new RuntimeException("设备不在线 id：" + id);
        }

        // 通过手机号获取 JT1078 设备信息
        // 或者使用 deviceCode 作为手机号
        R<Jt1078Device> deviceR = remoteJt1078Service.getDeviceByMobileNo(qsDevice.getJtMobileNo(), SecurityConstants.INNER);
        if (deviceR.getCode() != Constants.SUCCESS) {
            throw new RuntimeException("jt1078 获取设备信息失败 mobileNo：" + qsDevice.getJtMobileNo());
        }
        Assert.notNull(deviceR.getData(), "jt1078 设备不存在 mobileNo：" + qsDevice.getJtMobileNo());

        if (!deviceR.getData().getOnline()) {
            throw new RuntimeException("jt1078 设备不在线 mobileNo：" + qsDevice.getJtMobileNo());
        }

        DeferredResult<R<StreamContent>> result = new DeferredResult<>(userSetting.getPlayTimeout().longValue());

        result.onTimeout(() -> {
            log.info("[jt1078 点播等待超时] deviceId：{}", id);
            // 释放rtpserver
            R<StreamContent> wvpResult = R.fail();
            wvpResult.setMsg("点播超时");
            result.setResult(wvpResult);

            inviteStreamService.removeInviteInfoByDeviceAndChannel(InviteSessionType.PLAY, qsDevice.getId());
            mediaServerService.stopJt1078Play(InviteSessionType.PLAY, qsDevice, deviceR.getData(), qsDevice.getJtMobileNo());
        });

        ErrorCallback<StreamInfo> callback = (code, msg, streamInfo) -> {
            if (code == InviteErrorCode.SUCCESS.getCode()) {
                R<StreamContent> r = R.ok();
                if (streamInfo != null) {
                    if (userSetting.getUseSourceIpAsStreamIp()) {
                        streamInfo = streamInfo.clone(); // 深拷贝
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

        mediaServerService.startJt1078Play(qsDevice, deviceR.getData(), callback);
        return result;
    }

    /**
     * jt1078 停止点播
     *
     * @param id 设备id
     * @return
     */
    @GetMapping("/stopJt1078Play/{id}")
    public AjaxResult stopJt1078Play(@PathVariable Long id) {
        log.info("[jt1078 停止点播] id：{} ", id);
        Assert.notNull(id, "设备id");

        R<QsDevice> qsDevicer = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (qsDevicer.getCode() != Constants.SUCCESS) {
            throw new RuntimeException("获取设备信息失败 id：" + id);
        }
        Assert.notNull(qsDevicer.getData(), "设备不存在 id：" + id);

        QsDevice qsDevice = qsDevicer.getData();

        if ("OFFLINE".equals(qsDevice.getDeviceStatus())) {
            throw new RuntimeException("设备不在线 id：" + id);
        }

        // 通过手机号获取 JT1078 设备信息
        R<Jt1078Device> deviceR = remoteJt1078Service.getDeviceByMobileNo(qsDevice.getJtMobileNo(), SecurityConstants.INNER);
        if (deviceR.getCode() != Constants.SUCCESS) {
            throw new RuntimeException("jt1078 获取设备信息失败 mobileNo：" + qsDevice.getJtMobileNo());
        }
        Assert.notNull(deviceR.getData(), "jt1078 设备不存在 mobileNo：" + qsDevice.getJtMobileNo());

        if (!deviceR.getData().getOnline()) {
            throw new RuntimeException("jt1078 设备不在线 mobileNo：" + qsDevice.getJtMobileNo());
        }

        mediaServerService.stopJt1078Play(InviteSessionType.PLAY, qsDevice, deviceR.getData(), qsDevice.getDeviceCode());
        JSONObject json = new JSONObject();
        json.put("deviceId", id);
        json.put("jtMobileNo", qsDevice.getJtMobileNo());
        return AjaxResult.success(json);
    }

    /**
     * 内部接口：从流中获取截图
     *
     * @param app 应用名
     * @param stream 流名
     * @return 截图文件路径
     */
    @PostMapping("/api/zlm/getSnap")
    public R<String> getSnapInternal(@RequestParam String app, @RequestParam String stream) {
        ZlmMediaServer mediaServer = mediaServerService.getMediaServerForMinimumLoad(null);
        if (mediaServer == null) {
            throw new RuntimeException("无可用的流媒体服务器");
        }
        String filePath = mediaServerService.snapOnPlay(mediaServer, app, stream);
        return R.ok(filePath);
    }

    /**
     * 新的抓图接口，每次生成不同的文件名
     *
     * @param app    app
     * @param stream stream
     * @return
     */
    @PostMapping("/api/zlm/snap")
    public R<String> snap(@RequestParam String app, @RequestParam String stream) {
        ZlmMediaServer mediaServer = mediaServerService.getMediaServerForMinimumLoad(null);
        if (mediaServer == null) {
            throw new RuntimeException("无可用的流媒体服务器");
        }
        String filePath = mediaServerService.snap(mediaServer, app, stream);
        return R.ok(filePath);
    }

}
