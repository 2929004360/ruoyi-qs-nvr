package com.ruoyi.haikang.isup.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.haikang.isup.api.RemoteHaiKangIsupService;
import com.ruoyi.haikang.isup.api.domain.HaiKangIsupDeviceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * 海康isup服务降级处理
 *
 * @FileName RemoteHaiKangIsupFallbackFactory
 * @Description
 * @Author fengcheng
 * @date 2026-03-30
 **/
public class RemoteHaiKangIsupFallbackFactory implements FallbackFactory<RemoteHaiKangIsupService> {

    private static final Logger log = LoggerFactory.getLogger(RemoteHaiKangIsupFallbackFactory.class);

    @Override
    public RemoteHaiKangIsupService create(Throwable throwable) {
        log.error("海康isup服务调用失败:{}", throwable.getMessage());

        return new RemoteHaiKangIsupService() {

            @Override
            public R<Integer> getUserId(String ip, String source) {
                return R.fail("海康isup获取设备登录的用户ID失败:" + throwable.getMessage());
            }

            @Override
            public R<HaiKangIsupDeviceInfo> getDevInfo(String ip, String source) {
                return R.fail("海康isup获取设备信息失败:" + throwable.getMessage());
            }
        };
    }
}
