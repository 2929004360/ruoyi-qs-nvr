package com.ruoyi.onvif.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.onvif.api.RemoteOnvifService;
import com.ruoyi.onvif.api.domain.OnvifDevice;
import com.ruoyi.onvif.api.domain.WSOnvifDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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

            @Override
            public R<Void> startPtzControl(String deviceIp, String username, String password, String direction, Integer speed, String source) {
                return R.fail("onvif云台控制失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> stopPtzControl(String deviceIp, String username, String password, String source) {
                return R.fail("onvif云台停止失败:" + throwable.getMessage());
            }

            @Override
            public R<List<Map<String, Object>>> getPresets(String deviceIp, String username, String password, String source) {
                return R.fail("获取onvif预置点失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> setPreset(String deviceIp, String username, String password, Integer presetIndex, String presetName, String source) {
                return R.fail("设置onvif预置点失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> gotoPreset(String deviceIp, String username, String password, Integer presetIndex, Integer speed, String source) {
                return R.fail("调用onvif预置点失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> removePreset(String deviceIp, String username, String password, Integer presetIndex, String source) {
                return R.fail("删除onvif预置点失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> controlLight(String deviceIp, String username, String password, boolean on, String source) {
                return R.fail("onvif灯光控制失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> controlWiper(String deviceIp, String username, String password, boolean on, String source) {
                return R.fail("onvif雨刷控制失败:" + throwable.getMessage());
            }
        };
    }
}
