package com.ruoyi.zlm.api.factory;

import com.ruoyi.zlm.api.RemoteZlmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * zlm接口服务降级处理
 *
 * @FileName RemoteZlmFallbackFactory
 * @Description
 * @Author fengcheng
 * @date 2026-03-28
 **/
@Component
public class RemoteZlmFallbackFactory implements FallbackFactory<RemoteZlmService> {

    private static final Logger log = LoggerFactory.getLogger(RemoteZlmFallbackFactory.class);

    @Override
    public RemoteZlmService create(Throwable throwable) {
        log.error("zlm服务调用失败:{}", throwable.getMessage());

        return new RemoteZlmService() {

        };
    }
}
