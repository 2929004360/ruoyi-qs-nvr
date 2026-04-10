package com.ruoyi.zlm.controller;

import com.alibaba.nacos.api.model.v2.ErrorCode;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.enums.LiveStreamType;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.zlm.api.domain.*;
import com.ruoyi.zlm.common.InviteErrorCode;
import com.ruoyi.zlm.config.UserSetting;
import com.ruoyi.zlm.domain.Snap;
import com.ruoyi.zlm.service.ErrorCallback;
import com.ruoyi.zlm.service.IMediaServerService;
import com.ruoyi.zlm.utils.ZLMRESTfulUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.net.MalformedURLException;
import java.net.URL;

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

}
