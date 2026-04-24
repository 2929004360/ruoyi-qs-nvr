package com.ruoyi.gb28181.api;


import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.gb28181.api.domain.DeviceChannel;
import com.ruoyi.gb28181.api.factory.RemoteGb28181FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * gb28181 服务
 *
 * @FileName RemoteGb28181Service
 * @Description
 * @Author fengcheng
 * @date 2026-03-30
 **/
@FeignClient(contextId = "remoteGb28181Service", value = ServiceNameConstants.GB28181_SERVICE, fallbackFactory = RemoteGb28181FallbackFactory.class)
public interface RemoteGb28181Service {


    /**
     * 根据设备id获取设备
     *
     * @param gbDeviceId
     * @param inner
     * @return
     */
    @GetMapping("/api/gb28181/getDeviceByDeviceId/{gbDeviceId}")
    R<Device> getDeviceByDeviceId(@PathVariable String gbDeviceId, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 请求预览视频流
     *
     * @param rtpServer
     * @param inner
     * @return
     */
    @PostMapping("/api/gb28181/playStreamCmd")
    R<Void> playStreamCmd(@RequestBody RtpServerParam rtpServer, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 根据设备id和通道获取设备通道
     *
     * @param gbDeviceId
     * @param gbChannelId
     * @param inner
     * @return
     */
    @GetMapping("/api/gb28181/getDeviceChannelByChannelId/{gbDeviceId}/{gbChannelId}")
    R<DeviceChannel> getDeviceChannelByChannelId(@PathVariable String gbDeviceId, @PathVariable String gbChannelId, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 停止视频流
     *
     * @param rtpServer
     * @param inner
     * @return
     */
    @PostMapping("/api/gb28181/streamByeCmd")
    R<Void> streamByeCmd(@RequestBody RtpServerParam rtpServer, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);
}
