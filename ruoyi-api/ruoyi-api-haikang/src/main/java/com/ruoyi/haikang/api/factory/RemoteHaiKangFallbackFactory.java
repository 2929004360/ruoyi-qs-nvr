package com.ruoyi.haikang.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.haikang.api.RemoteHaiKangService;
import com.ruoyi.haikang.api.domain.HaikangDeviceInfo;
import com.ruoyi.haikang.api.domain.LoginDevice;
import com.ruoyi.haikang.api.domain.PresetInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

            @Override
            public R<Void> startPlayControl(Long deviceId, int channelId, String direction, String source) {
                return R.fail("海康sdk开始云台控制失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> endPlayControl(Long deviceId, int channelId, String direction, String source) {
                return R.fail("海康sdk结束云台控制失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> restartDevice(Long deviceId, String source) {
                return R.fail("海康sdk重启设备失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> shutDown(Long deviceId, String source) {
                return R.fail("海康sdk关闭设备失败:" + throwable.getMessage());
            }

            @Override
            public R<HashMap<String, Object>> getCurrentAudio(Long deviceId, int channelId, String source) {
                return R.fail("海康sdk获取设备音频编码参数失败:" + throwable.getMessage());
            }

            @Override
            public R<List<PresetInfo>> getPresets(Long deviceId, int channelId, String source) {
                return R.fail("海康sdk获取预置点列表失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> setPresets(Long deviceId, int channelId, int presetIndex, String source) {
                return R.fail("海康sdk设置预置点失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> delPresets(Long deviceId, int channelId, int presetIndex, String source) {
                return R.fail("海康sdk清除预置点失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> invokePresets(Long deviceId, int channelId, int presetIndex, String source) {
                return R.fail("海康sdk调用预置点失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> cameraAuxControl(Long deviceId, int channelId, String operation, boolean isStart, String source) {
                return R.fail("海康sdk辅助设备控制失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> cruiseControl(Long deviceId, int channelId, String operation, Integer param, String source) {
                return R.fail("海康sdk巡航控制失败:" + throwable.getMessage());
            }

            @Override
            public R<HashMap<String, Object>> getPTZcfg(Long deviceId, int channelId, String source) {
                return R.fail("海康sdk获取球机PTZ参数失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> setPTZcfg(Long deviceId, int channelId, short p, short t, short z, String source) {
                return R.fail("海康sdk设置球机PTZ参数失败:" + throwable.getMessage());
            }

            @Override
            public R<HashMap<String, Object>> getPTZAbsoluteEx(Long deviceId, int channelId, String source) {
                return R.fail("海康sdk获取高精度PTZ绝对位置配置失败:" + throwable.getMessage());
            }

            @Override
            public R<String> getDevTime(Long deviceId, String source) {
                return R.fail("海康sdk获取设备时间参数失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> setDevTime(Long deviceId, String time, String source) {
                return R.fail("海康sdk设置设备时间参数失败:" + throwable.getMessage());
            }

            @Override
            public R<ArrayList<HashMap<String, Object>>> getRecMonth(Long deviceId, int channelId, String startTime, String endTime, String source) {
                return R.fail("海康sdk查询录像失败:" + throwable.getMessage());
            }
        };
    }
}
