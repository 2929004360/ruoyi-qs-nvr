package com.ruoyi.haikang.isup.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.haikang.isup.api.RemoteHaiKangIsupService;
import com.ruoyi.haikang.isup.api.domain.HaiKangIsupDeviceInfo;
import com.ruoyi.haikang.isup.api.domain.HaiKangIsupPresetInfo;

import java.util.List;
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

            @Override
            public R<Void> startPlay(RtpServerParam rtpServerParam, String inner) {
                return R.fail("海康isup开始播放失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> stopPlay(Long id, String inner) {
                return R.fail("海康isup停止播放失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> startPtz(Long deviceId, Integer channelId, int PTZCmd, int speed, String inner) {
                return R.fail("海康isup开始云台控制失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> endPtz(Long deviceId, Integer channelId, int PTZCmd, int speed, String inner) {
                return R.fail("海康isup结束云台控制失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> setPreset(Long deviceId, Integer channelId, int presetIndex, String inner) {
                return R.fail("海康isup设置预置点失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> clearPreset(Long deviceId, Integer channelId, int presetIndex, String inner) {
                return R.fail("海康isup清除预置点失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> gotoPreset(Long deviceId, Integer channelId, int presetIndex, String inner) {
                return R.fail("海康isup调用预置点失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> cameraAuxControl(Long deviceId, Integer channelId, String operation, boolean isStart, String inner) {
                return R.fail("海康isup辅助设备控制失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> cruiseControl(Long deviceId, Integer channelId, String operation, Integer param, String inner) {
                return R.fail("海康isup巡航控制失败:" + throwable.getMessage());
            }

            @Override
            public R<List<HaiKangIsupPresetInfo>> getPresetList(Long deviceId, Integer channelId, String inner) {
                return R.fail("海康isup获取预置点列表失败:" + throwable.getMessage());
            }
        };
    }
}
