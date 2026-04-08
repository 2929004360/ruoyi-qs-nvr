package com.ruoyi.haikang.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.haikang.api.RemoteHaiKangService;
import com.ruoyi.haikang.api.domain.HaikangDeviceInfo;
import com.ruoyi.haikang.api.domain.LoginDevice;
import com.ruoyi.common.core.domain.RtpServerParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 海康sdk服务降级处理
 *
 * @FileName RemoteHaiKangFallbackFactory
 * @Description
 * @Author fengcheng
 * @date 2026-03-28
 **/
@Component
public class RemoteHaiKangFallbackFactory implements FallbackFactory<RemoteHaiKangService> {

    private static final Logger log = LoggerFactory.getLogger(RemoteHaiKangFallbackFactory.class);

    @Override
    public RemoteHaiKangService create(Throwable throwable) {
        log.error("海康sdk服务调用失败:{}", throwable.getMessage());
        return new RemoteHaiKangService() {
            @Override
            public R<Integer> loginDevice(LoginDevice loginDevice, String source) {
                return R.fail("海康sdk登录设备失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> logoutDevice(String ip, String source) {
                return R.fail("海康sdk设备注销失败:" + throwable.getMessage());
            }

            @Override
            public R<Integer> getUserId(String ip, String source) {
                return R.fail("海康sdk获取设备登录的用户ID失败:" + throwable.getMessage());
            }

            @Override
            public R<HaikangDeviceInfo> getDeviceInfo(String ipAddress, String source) {
                return R.fail("海康sdk获取设备的基本参数失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> startPlay(RtpServerParam rtpServerParam, String source) {
                return R.fail("海康sdk开始播放失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> stopPlay(Long id, String inner) {
                return R.fail("海康sdk停止播放失败:" + throwable.getMessage());
            }
        };
    }
}
