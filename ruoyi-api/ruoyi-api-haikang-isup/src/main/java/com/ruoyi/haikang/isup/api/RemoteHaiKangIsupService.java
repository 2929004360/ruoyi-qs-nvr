package com.ruoyi.haikang.isup.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.haikang.isup.api.domain.HaiKangIsupDeviceInfo;
import com.ruoyi.haikang.isup.api.factory.RemoteHaiKangIsupFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 海康isup服务
 *
 * @FileName RemoteHaiKangIsupService
 * @Description
 * @Author fengcheng
 * @date 2026-03-30
 **/
@FeignClient(contextId = "remoteHaiKangIsupService", value = ServiceNameConstants.HAIKANG_ISUP_SERVICE, fallbackFactory = RemoteHaiKangIsupFallbackFactory.class)
public interface RemoteHaiKangIsupService {

    /**
     * 获取设备登录的用户ID
     *
     * @param ip     设备ip
     * @param source 请求来源
     * @return
     */
    @PostMapping("/api/haikang/isup/getUserId/{ip}")
    R<Integer> getUserId(@PathVariable String ip, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 获取设备信息
     *
     * @param ip     设备ip
     * @param source 请求来源
     * @return
     */
    @PostMapping("/api/haikang/isup/getDevInfo/{ip}")
    R<HaiKangIsupDeviceInfo> getDevInfo(@PathVariable String ip, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 开始播放
     *
     * @param rtpServerParam 播放参数
     * @param inner          请求来源
     */
    @PostMapping("/api/haikang/isup/startPlay")
    public R<Void> startPlay(@RequestBody RtpServerParam rtpServerParam, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 停止播放
     *
     * @param id    失败id
     * @param inner 请求来源
     */
    @GetMapping("/api/haikang/isup/stopPlay/{id}")
    public R<Void> stopPlay(@PathVariable Long id, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);
}
