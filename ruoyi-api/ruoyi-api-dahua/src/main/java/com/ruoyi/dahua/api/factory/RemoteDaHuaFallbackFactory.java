package com.ruoyi.dahua.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.dahua.api.RemoteDaHuaService;
import com.ruoyi.dahua.api.domain.DahuaDevice;
import com.ruoyi.dahua.api.domain.LoginDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 大华sdk服务降级处理
 *
 * @FileName RemoteDaHuaFallbackFactory
 * @Description
 * @Author fengcheng
 * @date 2026-03-30
 **/
@Component
public class RemoteDaHuaFallbackFactory implements FallbackFactory<RemoteDaHuaService> {

    private static final Logger log = LoggerFactory.getLogger(RemoteDaHuaFallbackFactory.class);

    @Override
    public RemoteDaHuaService create(Throwable throwable) {
        log.error("大华sdk服务调用失败:{}", throwable.getMessage());
        return new RemoteDaHuaService() {
            @Override
            public R<Void> loginDevice(LoginDevice loginDevice, String source) {
                return R.fail("大华sdk登录设备失败:" + throwable.getMessage());
            }

            @Override
            public R<Boolean> isUserId(String ip, String source) {
                return R.fail("大华sdk查询是否登录失败:" + throwable.getMessage());
            }

            @Override
            public R<String> getTime(String ip, String source) {
                return R.fail("大华sdk设备获取时间失败:" + throwable.getMessage());
            }

            @Override
            public R<DahuaDevice> getDahuaDevice(String ip, String source) {
                return R.fail("大华sdk获取主动上线设备失败:" + throwable.getMessage());
            }

            @Override
            public R<Boolean> logoutDevice(String ip, String source) {
                return R.fail("大华sdk退出设备失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> startPlay(RtpServerParam rtpServerParam, String source) {
                return R.fail("大华sdk开始播放失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> stopPlay(Long id, String source) {
                return R.fail("大华sdk停止播放失败:" + throwable.getMessage());
            }
        };
    }
}
