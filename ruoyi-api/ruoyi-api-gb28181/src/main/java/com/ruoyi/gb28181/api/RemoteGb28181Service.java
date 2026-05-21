package com.ruoyi.gb28181.api;


import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.gb28181.api.domain.CatalogRequest;
import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.gb28181.api.domain.DeviceChannel;
import com.ruoyi.gb28181.api.domain.DeviceConfig;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.gb28181.api.domain.Gb28181Platform;
import com.ruoyi.gb28181.api.factory.RemoteGb28181FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * gb28181 服务
 *
 * @FileName RemoteGb28181Service
 * @Description
 * @Author fengcheng
 * @date 2026-03-30
 **/
@FeignClient(contextId = "remoteGb28181Service", value = ServiceNameConstants.GB28181_SERVICE, fallbackFactory = RemoteGb28181FallbackFactory.class)
public interface RemoteGb28181Service {


    /**
     * 根据设备id获取设备
     *
     * @param gbDeviceId
     * @param inner
     * @return
     */
    @GetMapping("/api/gb28181/getDeviceByDeviceId/{gbDeviceId}")
    R<Device> getDeviceByDeviceId(@PathVariable String gbDeviceId, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 请求预览视频流
     *
     * @param rtpServer
     * @param inner
     * @return
     */
    @PostMapping("/api/gb28181/playStreamCmd")
    R<Void> playStreamCmd(@RequestBody RtpServerParam rtpServer, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 请求回放视频流
     *
     * @param rtpServer
     * @param inner
     * @return
     */
    @PostMapping("/api/gb28181/playbackStreamCmd")
    R<Void> playbackStreamCmd(@RequestBody RtpServerParam rtpServer, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 根据设备id和通道获取设备通道
     *
     * @param gbDeviceId
     * @param gbChannelId
     * @param inner
     * @return
     */
    @GetMapping("/api/gb28181/getDeviceChannelByChannelId/{gbDeviceId}/{gbChannelId}")
    R<DeviceChannel> getDeviceChannelByChannelId(@PathVariable String gbDeviceId, @PathVariable String gbChannelId, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 停止视频流
     *
     * @param rtpServer
     * @param inner
     * @return
     */
    @PostMapping("/api/gb28181/streamByeCmd")
    R<Void> streamByeCmd(@RequestBody RtpServerParam rtpServer, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 获取全部设备
     *
     * @param inner
     * @return
     */
    @GetMapping("/api/gb28181/getAllDevices")
    R<List<Device>> getAllDevices(@RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 通用前端控制命令(参考国标文档A.3.1指令格式)
     *
     * @param deviceId     设备国标编号
     * @param channelId    通道国标编号
     * @param cmdCode      指令码(对应国标文档指令格式中的字节4)
     * @param parameter1   数据一(对应国标文档指令格式中的字节5, 范围0-255)
     * @param parameter2   数据二(对应国标文档指令格式中的字节6, 范围0-255)
     * @param combindCode2 组合码二(对应国标文档指令格式中的字节7, 范围0-15)
     * @param inner
     */
    @GetMapping("/api/gb28181/common/ptz/{deviceId}/{channelId}")
    R<Void> frontEndCommand(@PathVariable String deviceId, @PathVariable String channelId,
                           @RequestParam Integer cmdCode, @RequestParam Integer parameter1,
                           @RequestParam Integer parameter2, @RequestParam Integer combindCode2,
                           @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 云台控制
     *
     * @param deviceId      设备国标编号
     * @param channelId     通道国标编号
     * @param command       控制指令,允许值: left, right, up, down, upleft, upright, downleft, downright, zoomin, zoomout, stop
     * @param horizonSpeed  水平速度(0-255)
     * @param verticalSpeed 垂直速度(0-255)
     * @param zoomSpeed     缩放速度(0-15)
     * @param inner
     */
    @GetMapping("/api/gb28181/ptz/{deviceId}/{channelId}")
    R<Void> ptz(@PathVariable String deviceId, @PathVariable String channelId,
               @RequestParam String command, @RequestParam Integer horizonSpeed,
               @RequestParam Integer verticalSpeed, @RequestParam Integer zoomSpeed,
               @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 光圈控制
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param command   控制指令,允许值: in, out, stop
     * @param speed     光圈速度(0-255)
     * @param inner
     */
    @GetMapping("/api/gb28181/fi/iris/{deviceId}/{channelId}")
    R<Void> iris(@PathVariable String deviceId, @PathVariable String channelId,
                @RequestParam String command, @RequestParam Integer speed,
                @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 聚焦控制
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param command   控制指令,允许值: near, far, stop
     * @param speed     聚焦速度(0-255)
     * @param inner
     */
    @GetMapping("/api/gb28181/fi/focus/{deviceId}/{channelId}")
    R<Void> focus(@PathVariable String deviceId, @PathVariable String channelId,
                 @RequestParam String command, @RequestParam Integer speed,
                 @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 查询预置位
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param inner
     * @return
     */
    @GetMapping("/api/gb28181/preset/query/{deviceId}/{channelId}")
    R<Object> queryPreset(@PathVariable String deviceId, @PathVariable String channelId,
                          @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 预置位指令-设置预置位
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param presetId  预置位编号(1-255)
     * @param inner
     */
    @GetMapping("/api/gb28181/preset/add/{deviceId}/{channelId}")
    R<Void> addPreset(@PathVariable String deviceId, @PathVariable String channelId,
                      @RequestParam Integer presetId, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 预置位指令-调用预置位
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param presetId  预置位编号(1-255)
     * @param inner
     */
    @GetMapping("/api/gb28181/preset/call/{deviceId}/{channelId}")
    R<Void> callPreset(@PathVariable String deviceId, @PathVariable String channelId,
                      @RequestParam Integer presetId, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 预置位指令-删除预置位
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param presetId  预置位编号(1-255)
     * @param inner
     */
    @GetMapping("/api/gb28181/preset/delete/{deviceId}/{channelId}")
    R<Void> deletePreset(@PathVariable String deviceId, @PathVariable String channelId,
                         @RequestParam Integer presetId, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 巡航指令-加入巡航点
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param cruiseId  巡航组号(0-255)
     * @param presetId  预置位编号(1-255)
     * @param inner
     */
    @GetMapping("/api/gb28181/cruise/point/add/{deviceId}/{channelId}")
    R<Void> addCruisePoint(@PathVariable String deviceId, @PathVariable String channelId,
                          @RequestParam Integer cruiseId, @RequestParam Integer presetId,
                          @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 巡航指令-删除一个巡航点
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param cruiseId  巡航组号(1-255)
     * @param presetId  预置位编号(0-255, 为0时删除整个巡航)
     * @param inner
     */
    @GetMapping("/api/gb28181/cruise/point/delete/{deviceId}/{channelId}")
    R<Void> deleteCruisePoint(@PathVariable String deviceId, @PathVariable String channelId,
                             @RequestParam Integer cruiseId, @RequestParam Integer presetId,
                             @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 巡航指令-设置巡航速度
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param cruiseId  巡航组号(0-255)
     * @param speed     巡航速度(1-4095)
     * @param inner
     */
    @GetMapping("/api/gb28181/cruise/speed/{deviceId}/{channelId}")
    R<Void> setCruiseSpeed(@PathVariable String deviceId, @PathVariable String channelId,
                          @RequestParam Integer cruiseId, @RequestParam Integer speed,
                          @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 巡航指令-设置巡航停留时间
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param cruiseId  巡航组号
     * @param time      巡航停留时间(1-4095)
     * @param inner
     */
    @GetMapping("/api/gb28181/cruise/time/{deviceId}/{channelId}")
    R<Void> setCruiseTime(@PathVariable String deviceId, @PathVariable String channelId,
                         @RequestParam Integer cruiseId, @RequestParam Integer time,
                         @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 巡航指令-开始巡航
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param cruiseId  巡航组号
     * @param inner
     */
    @GetMapping("/api/gb28181/cruise/start/{deviceId}/{channelId}")
    R<Void> startCruise(@PathVariable String deviceId, @PathVariable String channelId,
                       @RequestParam Integer cruiseId, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 巡航指令-停止巡航
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param cruiseId  巡航组号
     * @param inner
     */
    @GetMapping("/api/gb28181/cruise/stop/{deviceId}/{channelId}")
    R<Void> stopCruise(@PathVariable String deviceId, @PathVariable String channelId,
                      @RequestParam Integer cruiseId, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 扫描指令-开始自动扫描
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param scanId    扫描组号(0-255)
     * @param inner
     */
    @GetMapping("/api/gb28181/scan/start/{deviceId}/{channelId}")
    R<Void> startScan(@PathVariable String deviceId, @PathVariable String channelId,
                     @RequestParam Integer scanId, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 扫描指令-停止自动扫描
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param scanId    扫描组号(0-255)
     * @param inner
     */
    @GetMapping("/api/gb28181/scan/stop/{deviceId}/{channelId}")
    R<Void> stopScan(@PathVariable String deviceId, @PathVariable String channelId,
                    @RequestParam Integer scanId, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 扫描指令-设置自动扫描左边界
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param scanId    扫描组号(0-255)
     * @param inner
     */
    @GetMapping("/api/gb28181/scan/set/left/{deviceId}/{channelId}")
    R<Void> setScanLeft(@PathVariable String deviceId, @PathVariable String channelId,
                       @RequestParam Integer scanId, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 扫描指令-设置自动扫描右边界
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param scanId    扫描组号(0-255)
     * @param inner
     */
    @GetMapping("/api/gb28181/scan/set/right/{deviceId}/{channelId}")
    R<Void> setScanRight(@PathVariable String deviceId, @PathVariable String channelId,
                        @RequestParam Integer scanId, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 扫描指令-设置自动扫描速度
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param scanId    扫描组号(0-255)
     * @param speed     自动扫描速度(1-4095)
     * @param inner
     */
    @GetMapping("/api/gb28181/scan/set/speed/{deviceId}/{channelId}")
    R<Void> setScanSpeed(@PathVariable String deviceId, @PathVariable String channelId,
                        @RequestParam Integer scanId, @RequestParam Integer speed,
                        @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 辅助开关控制指令-雨刷控制
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param command   控制指令,允许值: on, off
     * @param inner
     */
    @GetMapping("/api/gb28181/wiper/{deviceId}/{channelId}")
    R<Void> wiper(@PathVariable String deviceId, @PathVariable String channelId,
                 @RequestParam String command, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 辅助开关控制指令
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param command   控制指令,允许值: on, off
     * @param switchId  开关编号
     * @param inner
     */
    @GetMapping("/api/gb28181/auxiliary/{deviceId}/{channelId}")
    R<Void> auxiliarySwitch(@PathVariable String deviceId, @PathVariable String channelId,
                           @RequestParam String command, @RequestParam Integer switchId,
                           @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 平台级联 - 注册
     *
     * @param platform 平台配置
     * @param inner
     * @return
     */
    @PostMapping("/api/gb28181/platform/cascade/register")
    R<Void> registerPlatform(@RequestBody Gb28181Platform platform, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 平台级联 - 注销
     *
     * @param platform 平台配置
     * @param inner
     * @return
     */
    @PostMapping("/api/gb28181/platform/cascade/unregister")
    R<Void> unregisterPlatform(@RequestBody Gb28181Platform platform, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 平台级联 - 发送心跳
     *
     * @param platform 平台配置
     * @param inner
     * @return
     */
    @PostMapping("/api/gb28181/platform/cascade/heartbeat")
    R<Void> sendHeartbeat(@RequestBody Gb28181Platform platform, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 平台级联 - 发送设备信息
     *
     * @param platform 平台配置
     * @param inner
     * @return
     */
    @PostMapping("/api/gb28181/platform/cascade/deviceInfo")
    R<Void> sendDeviceInfo(@RequestBody Gb28181Platform platform, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 平台级联 - 发送目录
     *
     * @param catalogRequest 目录请求
     * @param inner
     * @return
     */
    @PostMapping("/api/gb28181/platform/cascade/catalog")
    R<Void> sendCatalog(@RequestBody CatalogRequest catalogRequest, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 平台级联 - 启动单个平台
     *
     * @param platformId 平台ID
     * @param inner
     * @return
     */
    @PostMapping("/api/gb28181/platform/cascade/start/{platformId}")
    R<Void> startPlatformCascade(@PathVariable Long platformId, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 平台级联 - 停止单个平台
     *
     * @param platformId 平台ID
     * @param inner
     * @return
     */
    @PostMapping("/api/gb28181/platform/cascade/stop/{platformId}")
    R<Void> stopPlatformCascade(@PathVariable Long platformId, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 平台级联 - 重启单个平台
     *
     * @param platformId 平台ID
     * @param inner
     * @return
     */
    @PostMapping("/api/gb28181/platform/cascade/restart/{platformId}")
    R<Void> restartPlatformCascade(@PathVariable Long platformId, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 平台级联 - 手动推送目录
     *
     * @param platformId 平台ID
     * @param inner
     * @return
     */
    @PostMapping("/api/gb28181/platform/cascade/catalog/{platformId}")
    R<Void> pushCatalog(@PathVariable Long platformId, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 查询设备配置
     *
     * @param gbDeviceId 设备国标编号
     * @param configType 配置类型，多个用/分隔
     * @param inner
     * @return 设备配置
     */
    @GetMapping("/api/gb28181/config/{gbDeviceId}")
    R<Object> queryDeviceConfig(@PathVariable String gbDeviceId, @RequestParam String configType, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 修改设备配置
     *
     * @param gbDeviceId 设备国标编号
     * @param deviceConfig 设备配置
     * @param inner
     * @return 操作结果
     */
    @PostMapping("/api/gb28181/config/{gbDeviceId}")
    R<Object> updateDeviceConfig(@PathVariable String gbDeviceId, @RequestBody DeviceConfig deviceConfig, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 查询看守位
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @param inner
     * @return 看守位信息
     */
    @GetMapping("/api/gb28181/homePosition/{gbDeviceId}")
    R<Object> queryHomePosition(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 设置看守位
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @param deviceConfig 看守位配置
     * @param inner
     * @return 操作结果
     */
    @PostMapping("/api/gb28181/homePosition/{gbDeviceId}")
    R<Object> updateHomePosition(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId, @RequestBody DeviceConfig deviceConfig, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 查询巡航轨迹列表
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @param inner
     * @return 巡航轨迹列表
     */
    @GetMapping("/api/gb28181/cruiseTrackList/{gbDeviceId}")
    R<Object> queryCruiseTrackList(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 查询巡航轨迹
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @param number 轨迹编号：0-第一条轨迹，1-第二条轨迹
     * @param inner
     * @return 巡航轨迹信息
     */
    @GetMapping("/api/gb28181/cruiseTrack/{gbDeviceId}")
    R<Object> queryCruiseTrack(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId, @RequestParam Integer number, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * PTZ精准状态查询
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @param inner
     * @return PTZ精准状态信息
     */
    @GetMapping("/api/gb28181/ptzPosition/{gbDeviceId}")
    R<Object> queryPTZPosition(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 存储卡状态查询
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @param inner
     * @return 存储卡状态信息
     */
    @GetMapping("/api/gb28181/sdCardStatus/{gbDeviceId}")
    R<Object> querySDCardStatus(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 报警复位控制
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @param alarmMethod 报警方式（可选），0-全部，1-电话报警，2-设备报警，3-短信报警，4-GPS报警，5-视频报警，6-设备故障报警，7-其他报警
     * @param alarmType 报警类型（可选）
     * @param inner
     * @return 报警复位控制结果
     */
    @PostMapping("/api/gb28181/alarmReset/{gbDeviceId}")
    R<Object> alarmResetControl(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId, @RequestParam(required = false) String alarmMethod, @RequestParam(required = false) String alarmType, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 强制关键帧控制
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @param inner
     * @return 强制关键帧控制结果
     */
    @PostMapping("/api/gb28181/iFrame/{gbDeviceId}")
    R<Object> iFrameControl(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 看守位控制
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @param deviceConfig 设备配置，包含看守位配置
     * @param inner
     * @return 看守位控制结果
     */
    @PostMapping("/api/gb28181/homePosition/{gbDeviceId}")
    R<Object> homePositionControl(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId, @RequestBody DeviceConfig deviceConfig, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * PTZ精准控制
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @param ptzPreciseCtrl PTZ精准控制参数
     * @param inner
     * @return PTZ精准控制结果
     */
    @PostMapping("/api/gb28181/ptzPrecise/{gbDeviceId}")
    R<Object> ptzPreciseControl(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId, @RequestBody JSONObject ptzPreciseCtrl, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 设备软件升级控制
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @param firmware 设备固件版本
     * @param fileURL 升级文件的完整路径
     * @param manufacturer 设备厂商
     * @param sessionID 会话ID
     * @param inner
     * @return 设备软件升级控制结果
     */
    @PostMapping("/api/gb28181/deviceUpgrade/{gbDeviceId}")
    R<Object> deviceUpgradeControl(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId,
        @RequestParam String firmware, @RequestParam String fileURL, @RequestParam String manufacturer,
        @RequestParam String sessionID, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 存储卡格式化控制
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @param sdCardId SD卡编号（0表示所有存储卡）
     * @param inner
     * @return 存储卡格式化控制结果
     */
    @PostMapping("/api/gb28181/formatSDCard/{gbDeviceId}")
    R<Object> formatSDCardControl(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId,
        @RequestParam Integer sdCardId, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 目标跟踪控制
     *
     * @param gbDeviceId 设备国标编号（球机通道）
     * @param channelId 通道国标编号（可选，指球机通道）
     * @param targetTrack 跟踪类型：Auto/Manual/Stop
     * @param deviceId2 目标设备编码（可选，指全景相机中的全景通道ID）
     * @param targetArea 目标区域（可选，手动跟踪时需要）
     * @param inner
     * @return 目标跟踪控制结果
     */
    @PostMapping("/api/gb28181/targetTrack/{gbDeviceId}")
    R<Object> targetTrackControl(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId,
        @RequestParam String targetTrack, @RequestParam(required = false) String deviceId2,
        @RequestBody(required = false) JSONObject targetArea, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

     /**
     * 抓图控制
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @param snapshotType 抓图类型（可选）
     * @param inner
     * @return 抓图记录ID
     */
    @PostMapping("/api/gb28181/captureAndSave/{gbDeviceId}")
    R<Long> captureAndSave(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId,
        @RequestParam(required = false) String snapshotType, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 设备校时控制
     *
     * @param gbDeviceId 设备国标编号
     * @param inner
     * @return 校时结果
     */
    @PostMapping("/api/gb28181/timeCheck/{gbDeviceId}")
    R<Object> timeCheckCmd(@PathVariable String gbDeviceId, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

}
