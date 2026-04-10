package com.ruoyi.onvif.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.onvif.api.domain.OnvifDevice;
import com.ruoyi.onvif.api.domain.WSOnvifDevice;
import com.ruoyi.onvif.api.factory.RemoteOnvifFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * onvif 服务
 *
 * @FileName RemoteOnvifService
 * @Description
 * @Author fengcheng
 * @date 2026-04-10
 **/
@FeignClient(contextId = "remoteOnvifService", value = ServiceNameConstants.ONVIF_SERVICE, fallbackFactory = RemoteOnvifFallbackFactory.class)
public interface RemoteOnvifService {

    /**
     * 验证登录onvif设备
     *
     * @param onvifDevice 设备信息
     * @param source      请求来源
     * @return
     */
    @PostMapping("/api/onvif/login")
    R<OnvifDevice> login(@RequestBody WSOnvifDevice onvifDevice, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
