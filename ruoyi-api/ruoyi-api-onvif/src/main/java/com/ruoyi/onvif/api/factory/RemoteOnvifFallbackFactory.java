package com.ruoyi.onvif.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.onvif.api.RemoteOnvifService;
import com.ruoyi.onvif.api.domain.OnvifDevice;
import com.ruoyi.onvif.api.domain.WSOnvifDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * onvif服务降级处理
 *
 * @FileName RemoteOnvifFallbackFactory
 * @Description
 * @Author fengcheng
 * @date 2026-04-10
 **/
@Component
public class RemoteOnvifFallbackFactory implements FallbackFactory<RemoteOnvifService> {

    private static final Logger log = LoggerFactory.getLogger(RemoteOnvifService.class);

    @Override
    public RemoteOnvifService create(Throwable throwable) {
        log.error("onvif服务调用失败:{}", throwable.getMessage());
        return new RemoteOnvifService() {
            @Override
            public R<OnvifDevice> login(WSOnvifDevice onvifDevice, String source) {
                return R.fail("验证登录onvif设备失败:" + throwable.getMessage());
            }
        };
    }
}
