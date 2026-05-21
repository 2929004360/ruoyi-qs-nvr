package com.ruoyi.gb28181.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.gb28181.api.RemoteGb28181Service;
import com.ruoyi.gb28181.api.domain.CatalogRequest;
import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.gb28181.api.domain.DeviceChannel;
import com.ruoyi.gb28181.api.domain.DeviceConfig;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.gb28181.api.domain.Gb28181Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * gb28181服务降级处理
 *
 * @FileName RemoteGb28181FallbackFactory
 * @Description
 * @Author fengcheng
 * @date 2026-03-30
 **/
@Component
public class RemoteGb28181FallbackFactory implements FallbackFactory<RemoteGb28181Service> {

    private static final Logger log = LoggerFactory.getLogger(RemoteGb28181FallbackFactory.class);

    @Override
    public RemoteGb28181Service create(Throwable throwable) {
        log.error("gb28181服务调用失败:{}", throwable.getMessage());
        return new RemoteGb28181Service() {
            @Override
            public R<Device> getDeviceByDeviceId(String gbDeviceId, String inner) {
                return R.fail("gb28181 根据设备id获取设备失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> playStreamCmd(RtpServerParam rtpServer, String inner) {
                return R.fail("gb28181 请求预览视频流失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> playbackStreamCmd(RtpServerParam rtpServer, String inner) {
                return R.fail("gb28181 请求回放视频流失败:" + throwable.getMessage());
            }

            @Override
            public R<DeviceChannel> getDeviceChannelByChannelId(String gbDeviceId, String gbChannelId, String inner) {
                return R.fail("gb28181 根据设备id和通道获取设备通道:" + throwable.getMessage());
            }

            @Override
            public R<Void> streamByeCmd(RtpServerParam rtpServer, String inner) {
                return R.fail("gb28181 请求停止预览视频流失败:" + throwable.getMessage());
            }

            @Override
            public R<List<Device>> getAllDevices(String inner) {
                return R.fail("gb28181 获取全部设备失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> frontEndCommand(String deviceId, String channelId, Integer cmdCode, Integer parameter1, Integer parameter2, Integer combindCode2, String inner) {
                return R.fail("gb28181 通用前端控制命令失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> ptz(String deviceId, String channelId, String command, Integer horizonSpeed, Integer verticalSpeed, Integer zoomSpeed, String inner) {
                return R.fail("gb28181 云台控制失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> iris(String deviceId, String channelId, String command, Integer speed, String inner) {
                return R.fail("gb28181 光圈控制失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> focus(String deviceId, String channelId, String command, Integer speed, String inner) {
                return R.fail("gb28181 聚焦控制失败:" + throwable.getMessage());
            }

            @Override
            public R<Object> queryPreset(String deviceId, String channelId, String inner) {
                return R.fail("gb28181 查询预置位失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> addPreset(String deviceId, String channelId, Integer presetId, String inner) {
                return R.fail("gb28181 设置预置位失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> callPreset(String deviceId, String channelId, Integer presetId, String inner) {
                return R.fail("gb28181 调用预置位失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> deletePreset(String deviceId, String channelId, Integer presetId, String inner) {
                return R.fail("gb28181 删除预置位失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> addCruisePoint(String deviceId, String channelId, Integer cruiseId, Integer presetId, String inner) {
                return R.fail("gb28181 加入巡航点失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> deleteCruisePoint(String deviceId, String channelId, Integer cruiseId, Integer presetId, String inner) {
                return R.fail("gb28181 删除巡航点失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> setCruiseSpeed(String deviceId, String channelId, Integer cruiseId, Integer speed, String inner) {
                return R.fail("gb28181 设置巡航速度失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> setCruiseTime(String deviceId, String channelId, Integer cruiseId, Integer time, String inner) {
                return R.fail("gb28181 设置巡航停留时间失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> startCruise(String deviceId, String channelId, Integer cruiseId, String inner) {
                return R.fail("gb28181 开始巡航失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> stopCruise(String deviceId, String channelId, Integer cruiseId, String inner) {
                return R.fail("gb28181 停止巡航失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> startScan(String deviceId, String channelId, Integer scanId, String inner) {
                return R.fail("gb28181 开始自动扫描失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> stopScan(String deviceId, String channelId, Integer scanId, String inner) {
                return R.fail("gb28181 停止自动扫描失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> setScanLeft(String deviceId, String channelId, Integer scanId, String inner) {
                return R.fail("gb28181 设置自动扫描左边界失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> setScanRight(String deviceId, String channelId, Integer scanId, String inner) {
                return R.fail("gb28181 设置自动扫描右边界失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> setScanSpeed(String deviceId, String channelId, Integer scanId, Integer speed, String inner) {
                return R.fail("gb28181 设置自动扫描速度失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> wiper(String deviceId, String channelId, String command, String inner) {
                return R.fail("gb28181 雨刷控制失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> auxiliarySwitch(String deviceId, String channelId, String command, Integer switchId, String inner) {
                return R.fail("gb28181 辅助开关控制失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> registerPlatform(Gb28181Platform platform, String inner) {
                return R.fail("gb28181 平台级联注册失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> unregisterPlatform(Gb28181Platform platform, String inner) {
                return R.fail("gb28181 平台级联注销失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> sendHeartbeat(Gb28181Platform platform, String inner) {
                return R.fail("gb28181 平台级联发送心跳失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> sendDeviceInfo(Gb28181Platform platform, String inner) {
                return R.fail("gb28181 平台级联发送设备信息失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> sendCatalog(CatalogRequest catalogRequest, String inner) {
                return R.fail("gb28181 平台级联发送目录失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> startPlatformCascade(Long platformId, String inner) {
                return R.fail("gb28181 平台级联启动失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> stopPlatformCascade(Long platformId, String inner) {
                return R.fail("gb28181 平台级联停止失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> restartPlatformCascade(Long platformId, String inner) {
                return R.fail("gb28181 平台级联重启失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> pushCatalog(Long platformId, String inner) {
                return R.fail("gb28181 平台级联推送目录失败:" + throwable.getMessage());
            }

            @Override
            public R<Object> queryDeviceConfig(String gbDeviceId, String configType, String inner) {
                return R.fail("gb28181 查询设备配置失败:" + throwable.getMessage());
            }

            @Override
            public R<Object> updateDeviceConfig(String gbDeviceId, DeviceConfig deviceConfig, String inner) {
                return R.fail("gb28181 修改设备配置失败:" + throwable.getMessage());
            }

            @Override
            public R<Object> queryHomePosition(String gbDeviceId, String channelId, String inner) {
                return R.fail("gb28181 查询看守位失败:" + throwable.getMessage());
            }

            @Override
            public R<Object> updateHomePosition(String gbDeviceId, String channelId, DeviceConfig deviceConfig, String inner) {
                return R.fail("gb28181 设置看守位失败:" + throwable.getMessage());
            }

            @Override
            public R<Object> queryCruiseTrackList(String gbDeviceId, String channelId, String inner) {
                return R.fail("gb28181 查询巡航轨迹列表失败:" + throwable.getMessage());
            }

            @Override
            public R<Object> queryCruiseTrack(String gbDeviceId, String channelId, Integer number, String inner) {
                return R.fail("gb28181 查询巡航轨迹失败:" + throwable.getMessage());
            }

            @Override
            public R<Object> queryPTZPosition(String gbDeviceId, String channelId, String inner) {
                return R.fail("gb28181 PTZ精准状态查询失败:" + throwable.getMessage());
            }

            @Override
            public R<Object> querySDCardStatus(String gbDeviceId, String channelId, String inner) {
                return R.fail("gb28181 存储卡状态查询失败:" + throwable.getMessage());
            }

            @Override
            public R<Object> alarmResetControl(String gbDeviceId, String channelId, String alarmMethod, String alarmType, String inner) {
                return R.fail("gb28181 报警复位控制失败:" + throwable.getMessage());
            }

            @Override
            public R<Object> iFrameControl(String gbDeviceId, String channelId, String inner) {
                return R.fail("gb28181 强制关键帧控制失败:" + throwable.getMessage());
            }

            @Override
            public R<Object> homePositionControl(String gbDeviceId, String channelId, DeviceConfig deviceConfig, String inner) {
                return R.fail("gb28181 看守位控制失败:" + throwable.getMessage());
            }

            @Override
            public R<Object> ptzPreciseControl(String gbDeviceId, String channelId, JSONObject ptzPreciseCtrl, String inner) {
                return R.fail("gb28181 PTZ精准控制失败:" + throwable.getMessage());
            }

            @Override
            public R<Object> deviceUpgradeControl(String gbDeviceId, String channelId, String firmware, String fileURL, String manufacturer, String sessionID, String inner) {
                return R.fail("gb28181 设备软件升级控制失败:" + throwable.getMessage());
            }

            @Override
            public R<Object> formatSDCardControl(String gbDeviceId, String channelId, Integer sdCardId, String inner) {
                return R.fail("gb28181 存储卡格式化控制失败:" + throwable.getMessage());
            }

            @Override
            public R<Object> targetTrackControl(String gbDeviceId, String channelId, String targetTrack, String deviceId2, JSONObject targetArea, String inner) {
                return R.fail("gb28181 目标跟踪控制失败:" + throwable.getMessage());
            }

            @Override
            public R<Long> captureAndSave(String gbDeviceId, String channelId, String snapshotType, String inner) {
                return R.fail("gb28181 抓图控制失败:" + throwable.getMessage());
            }

            @Override
            public R<Object> timeCheckCmd(String gbDeviceId, String inner) {
                return R.fail("gb28181 设备校时控制失败:" + throwable.getMessage());
            }

            
        };
    }
}
