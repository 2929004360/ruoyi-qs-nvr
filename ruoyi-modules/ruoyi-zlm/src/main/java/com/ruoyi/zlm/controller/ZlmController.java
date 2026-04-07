package com.ruoyi.zlm.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.zlm.api.domain.StreamContent;
import com.ruoyi.zlm.api.domain.StreamInfo;
import com.ruoyi.zlm.api.domain.StreamPullPlay;
import com.ruoyi.zlm.common.InviteErrorCode;
import com.ruoyi.zlm.config.UserSetting;
import com.ruoyi.zlm.service.ErrorCallback;
import com.ruoyi.zlm.service.IMediaServerService;
import com.ruoyi.zlm.utils.ZLMRESTfulUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
                    if (!ObjectUtils.isEmpty(streamInfo.getMediaServer().getTranscodeSuffix())
                            && !"null".equalsIgnoreCase(streamInfo.getMediaServer().getTranscodeSuffix())) {
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
}
