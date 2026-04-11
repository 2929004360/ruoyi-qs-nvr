package com.ruoyi.zlm.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.zlm.api.RemoteZlmCloudRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * zlm接口云端接口 服务降级处理
 *
 * @FileName RemoteZlmCloudRecordFallbackFactory
 * @Description
 * @Author fengcheng
 * @date 2026-04-11
 **/
@Component
public class RemoteZlmCloudRecordFallbackFactory implements FallbackFactory<RemoteZlmCloudRecordService> {

    private static final Logger log = LoggerFactory.getLogger(RemoteZlmFallbackFactory.class);

    @Override
    public RemoteZlmCloudRecordService create(Throwable throwable) {

        log.error("zlm接口云端调用失败:{}", throwable.getMessage());

        return new RemoteZlmCloudRecordService(){
            @Override
            public R<Void> task(String inner) {
                return R.fail("zlm接口云端定时查询待删除的录像文件接口失败:" + throwable.getMessage());
            }
        };
    }
}
