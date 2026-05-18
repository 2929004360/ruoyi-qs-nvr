package com.ruoyi.dahua.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.dahua.api.RemoteDaHuaService;
import com.ruoyi.dahua.api.domain.DahuaDevice;
import com.ruoyi.dahua.api.domain.LoginDevice;
import com.ruoyi.dahua.api.domain.DahuaRecordDownloadRequest;
import com.ruoyi.dahua.api.domain.DahuaRecordDownloadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

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
            public R<Boolean> setTime(Long id, String date, boolean type, String source) {
                return R.fail("大华sdk设备设置时间失败:" + throwable.getMessage());
            }

            @Override
            public R<Boolean> reboot(Long id, String source) {
                return R.fail("大华sdk设备重启失败:" + throwable.getMessage());
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

            @Override
            public R<Boolean> ptzControlUpStart(Long id, int channelId, String direction, Integer speed, String source) {
                return R.fail("大华sdk云台控制开始失败:" + throwable.getMessage());
            }

            @Override
            public R<Boolean> ptzControlUpEnd(Long id, int channelId, String direction, String source) {
                return R.fail("大华sdk云台控制停止失败:" + throwable.getMessage());
            }

            @Override
            public R<ArrayList<HashMap<String, Object>>> getPresetList(Long id, int channelId, String source) {
                return R.fail("大华sdk获取预置点列表失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> setPreset(Long id, int channelId, int presetIndex, String source) {
                return R.fail("大华sdk设置预置点失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> delPreset(Long id, int channelId, int presetIndex, String source) {
                return R.fail("大华sdk删除预置点失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> invokePreset(Long id, int channelId, int presetIndex, String source) {
                return R.fail("大华sdk调用预置点失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> controlLight(Long id, int channelId, int action, String source) {
                return R.fail("大华sdk灯光控制失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> controlWiper(Long id, int channelId, int action, String source) {
                return R.fail("大华sdk雨刷控制失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> startTour(Long id, int channelId, int tourIndex, String source) {
                return R.fail("大华sdk开始巡航失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> stopTour(Long id, int channelId, String source) {
                return R.fail("大华sdk停止巡航失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> addPresetToTour(Long id, int channelId, int tourIndex, int presetIndex, String source) {
                return R.fail("大华sdk添加预置点到巡航线路失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> removePresetFromTour(Long id, int channelId, int tourIndex, int presetIndex, String source) {
                return R.fail("大华sdk从巡航线路删除预置点失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> clearTour(Long id, int channelId, int tourIndex, String source) {
                return R.fail("大华sdk清除巡航线路失败:" + throwable.getMessage());
            }

            @Override
            public R<ArrayList<HashMap<String, Object>>> queryRecord(Long id, int channelId, String startTime, String endTime, String source) {
                return R.fail("大华sdk查询录像失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> startPlayback(RtpServerParam rtpServerParam, String source) {
                return R.fail("大华sdk开始录像回放失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> stopPlayback(Long id, String source) {
                return R.fail("大华sdk停止录像回放失败:" + throwable.getMessage());
            }

            @Override
            public R<Long> captureAndSave(Long id, int channelId, String snapshotType, String source) {
                return R.fail("大华sdk抓图并保存失败:" + throwable.getMessage());
            }

            @Override
            public R<DahuaRecordDownloadResponse> downloadRecord(DahuaRecordDownloadRequest request, String source) {
                return R.fail("大华sdk录像下载失败:" + throwable.getMessage());
            }
        };
    }
}
