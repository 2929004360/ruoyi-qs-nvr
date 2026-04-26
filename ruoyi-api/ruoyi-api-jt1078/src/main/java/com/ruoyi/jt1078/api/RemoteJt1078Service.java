package com.ruoyi.jt1078.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.jt1078.api.domain.Jt1078Device;
import com.ruoyi.jt1078.api.factory.RemoteJt1078FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * jt1078 服务
 *
 * @author fengcheng
 */
@FeignClient(contextId = "remoteJt1078Service", value = ServiceNameConstants.JT1078_SERVICE, fallbackFactory = RemoteJt1078FallbackFactory.class)
public interface RemoteJt1078Service {

    /**
     * 根据设备手机号获取设备
     *
     * @param mobileNo 设备手机号
     * @param inner
     * @return
     */
    @GetMapping("/api/jt1078/getDeviceByMobileNo/{mobileNo}")
    R<Jt1078Device> getDeviceByMobileNo(@PathVariable String mobileNo, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 请求预览视频流
     *
     * @param rtpServer
     * @param inner
     * @return
     */
    @PostMapping("/api/jt1078/playStreamCmd")
    R<Void> playStreamCmd(@RequestBody RtpServerParam rtpServer, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 停止视频流
     *
     * @param rtpServer
     * @param inner
     * @return
     */
    @PostMapping("/api/jt1078/streamByeCmd")
    R<Void> streamByeCmd(@RequestBody RtpServerParam rtpServer, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 获取全部设备
     *
     * @param inner
     * @return
     */
    @GetMapping("/api/jt1078/getAllDevices")
    R<List<Jt1078Device>> getAllDevices(@RequestHeader(SecurityConstants.FROM_SOURCE) String inner);
}
