package com.ruoyi.gb28181.api;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.gb28181.api.bean.RecordInfo;
import com.ruoyi.gb28181.api.bean.ErrorCallback;
import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.gb28181.api.domain.DeviceChannel;
import com.ruoyi.gb28181.api.domain.DeviceConfig;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.RemoteQsDeviceSnapshotService;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.qs.api.domain.QsDeviceSnapshot;
import com.ruoyi.gb28181.common.ErrorCode;
import com.ruoyi.gb28181.api.utils.DateUtil;
import com.ruoyi.gb28181.config.UserSetting;
import com.ruoyi.gb28181.service.IDeviceService;
import com.ruoyi.gb28181.service.IRedisCatchStorage;
import com.ruoyi.gb28181.service.ISIPCommander;
import com.ruoyi.gb28181.session.SipInviteSessionManager;
import com.ruoyi.zlm.api.RemoteZlmService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.sip.ResponseEvent;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;

/**
 * gb28181 Controller
 */
@Slf4j
@RestController
@RequestMapping("/api/gb28181")
public class Gb28181ApiController {

    @Autowired
    private IDeviceService deviceService;

    @Autowired
    private ISIPCommander sipCommander;

    @Autowired
    private UserSetting userSetting;

    @Autowired
    private SipInviteSessionManager sessionManager;

    @Autowired
    private RemoteZlmService remoteZlmService;

    @Autowired
    private IRedisCatchStorage redisCatchStorage;

    @Value("${file.path}")
    private String filePath;

    @Value("${file.domain}")
    private String fileDomain;

    @Value("${file.prefix}")
    private String filePrefix;

    @Autowired
    private RemoteQsDeviceService remoteQsDeviceService;

    @Autowired
    private RemoteQsDeviceSnapshotService remoteQsDeviceSnapshotService;

    /**
     * 根据设备id获取设备
     *
     * @param gbDeviceId
     * @return
     */
    @GetMapping("/getDeviceByDeviceId/{gbDeviceId}")
    R<Device> getDeviceByDeviceId(@PathVariable String gbDeviceId) {
        return R.ok(deviceService.getDeviceByDeviceId(gbDeviceId));
    }

    /**
     * 请求预览视频流
     *
     * @param rtpServer
     * @return
     */
    @PostMapping("/playStreamCmd")
    R<Boolean> playStreamCmd(@RequestBody RtpServerParam rtpServer) throws Exception {
        Device device = deviceService.getDeviceByDeviceId(rtpServer.getGbDeviceId());

        if (device == null) {
            throw new RuntimeException("国标设备不存在 deviceId:" + rtpServer.getGbDeviceId());
        }
        CompletableFuture<R<Boolean>> future = new CompletableFuture<>();

        try {
            sipCommander.playStreamCmd(device, rtpServer, (eventResult) -> {
                ResponseEvent responseEvent = (ResponseEvent) eventResult.event;
                String contentString = new String(responseEvent.getResponse().getRawContent());

                if (device.getStreamMode().equalsIgnoreCase("TCP-ACTIVE")) {
                    String substring = contentString.indexOf("y=") > 0
                            ? contentString.substring(0, contentString.indexOf("y="))
                            : contentString;

                    log.info("[TCP主动连接对方] deviceId: {}, channelId: {}, 连接对方的地址", rtpServer.getGbDeviceId(), rtpServer.getGbChannelId());
                    R<Boolean> r = remoteZlmService.connectRtpServer(rtpServer.getMediaServerId(), "", 0, rtpServer.getStream(), SecurityConstants.INNER);

                    if (r.getCode() != Constants.SUCCESS) {
                        sessionManager.removeByStream(rtpServer.getApp(), rtpServer.getStream());
                        remoteZlmService.releaseSsrc(rtpServer.getMediaServerId(), rtpServer.getSsrc(), SecurityConstants.INNER);
                        remoteZlmService.closeRTPServer(rtpServer.getMediaServerId(), rtpServer, SecurityConstants.INNER);
                        future.complete(R.ok(false, "[TCP主动连接对方] deviceId:" + rtpServer.getGbDeviceId() + ", channelId:" + rtpServer.getGbChannelId()));
                        return;
                    }

                    Boolean result = r.getData();
                    log.info("[TCP主动连接对方] 结果: {}", result);
                    if (!result) {
                        sessionManager.removeByStream(rtpServer.getApp(), rtpServer.getStream());
                        remoteZlmService.releaseSsrc(rtpServer.getMediaServerId(), rtpServer.getSsrc(), SecurityConstants.INNER);
                        remoteZlmService.closeRTPServer(rtpServer.getMediaServerId(), rtpServer, SecurityConstants.INNER);
                        future.complete(R.ok(false, "[TCP主动连接对方] deviceId:" + rtpServer.getGbDeviceId() + ", channelId:" + rtpServer.getGbChannelId()));
                    }
                }

                future.complete(R.ok(true, "国标28181请求预览视频流成功"));
            }, (event) -> {
                sessionManager.removeByStream(rtpServer.getApp(), rtpServer.getStream());
                remoteZlmService.releaseSsrc(rtpServer.getMediaServerId(), rtpServer.getSsrc(), SecurityConstants.INNER);
                remoteZlmService.closeRTPServer(rtpServer.getMediaServerId(), rtpServer, SecurityConstants.INNER);
                future.complete(R.fail("国标28181请求预览视频流失败"));
            }, userSetting.getPlayTimeout().longValue());
        } catch (Exception e) {
            log.error("发送国标播放sip错误 deviceId:{}", rtpServer.getGbDeviceId(), e);
            future.complete(R.fail(false, "国标28181请求预览视频流失败"));
            remoteZlmService.releaseSsrc(rtpServer.getMediaServerId(), rtpServer.getSsrc(), SecurityConstants.INNER);
            remoteZlmService.closeRTPServer(rtpServer.getMediaServerId(), rtpServer, SecurityConstants.INNER);
            sessionManager.removeByStream(rtpServer.getApp(), rtpServer.getStream());
        }

        try {
            return future.get(userSetting.getPlayTimeout().longValue(), TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("等待播放响应超时或出错 deviceId:{}", rtpServer.getGbDeviceId(), e);
            remoteZlmService.releaseSsrc(rtpServer.getMediaServerId(), rtpServer.getSsrc(), SecurityConstants.INNER);
            remoteZlmService.closeRTPServer(rtpServer.getMediaServerId(), rtpServer, SecurityConstants.INNER);
            sessionManager.removeByStream(rtpServer.getApp(), rtpServer.getStream());
            return R.fail(false, "国标28181请求预览视频流超时或出错");
        }
    }

    /**
     * 请求回放视频流
     *
     * @param rtpServer
     * @return
     */
    @PostMapping("/playbackStreamCmd")
    R<Boolean> playbackStreamCmd(@RequestBody RtpServerParam rtpServer) throws Exception {
        Device device = deviceService.getDeviceByDeviceId(rtpServer.getGbDeviceId());

        if (device == null) {
            throw new RuntimeException("国标设备不存在 deviceId:" + rtpServer.getGbDeviceId());
        }
        CompletableFuture<R<Boolean>> future = new CompletableFuture<>();

        try {
            sipCommander.playbackStreamCmd(device, rtpServer, (eventResult) -> {
                ResponseEvent responseEvent = (ResponseEvent) eventResult.event;
                String contentString = new String(responseEvent.getResponse().getRawContent());

                if (device.getStreamMode().equalsIgnoreCase("TCP-ACTIVE")) {
                    String substring = contentString.indexOf("y=") > 0
                            ? contentString.substring(0, contentString.indexOf("y="))
                            : contentString;

                    log.info("[TCP主动连接对方] deviceId: {}, channelId: {}, 连接对方的地址", rtpServer.getGbDeviceId(), rtpServer.getGbChannelId());
                    R<Boolean> r = remoteZlmService.connectRtpServer(rtpServer.getMediaServerId(), "", 0, rtpServer.getStream(), SecurityConstants.INNER);

                    if (r.getCode() != Constants.SUCCESS) {
                        sessionManager.removeByStream(rtpServer.getApp(), rtpServer.getStream());
                        remoteZlmService.releaseSsrc(rtpServer.getMediaServerId(), rtpServer.getSsrc(), SecurityConstants.INNER);
                        remoteZlmService.closeRTPServer(rtpServer.getMediaServerId(), rtpServer, SecurityConstants.INNER);
                        future.complete(R.ok(false, "[TCP主动连接对方] deviceId:" + rtpServer.getGbDeviceId() + ", channelId:" + rtpServer.getGbChannelId()));
                        return;
                    }

                    Boolean result = r.getData();
                    log.info("[TCP主动连接对方] 结果: {}", result);
                    if (!result) {
                        sessionManager.removeByStream(rtpServer.getApp(), rtpServer.getStream());
                        remoteZlmService.releaseSsrc(rtpServer.getMediaServerId(), rtpServer.getSsrc(), SecurityConstants.INNER);
                        remoteZlmService.closeRTPServer(rtpServer.getMediaServerId(), rtpServer, SecurityConstants.INNER);
                        future.complete(R.ok(false, "[TCP主动连接对方] deviceId:" + rtpServer.getGbDeviceId() + ", channelId:" + rtpServer.getGbChannelId()));
                    }
                }

                future.complete(R.ok(true, "国标28181请求回放视频流成功"));
            }, (event) -> {
                sessionManager.removeByStream(rtpServer.getApp(), rtpServer.getStream());
                remoteZlmService.releaseSsrc(rtpServer.getMediaServerId(), rtpServer.getSsrc(), SecurityConstants.INNER);
                remoteZlmService.closeRTPServer(rtpServer.getMediaServerId(), rtpServer, SecurityConstants.INNER);
                future.complete(R.fail("国标28181请求回放视频流失败"));
            }, userSetting.getPlayTimeout().longValue());
        } catch (Exception e) {
            log.error("发送国标回放sip错误 deviceId:{}", rtpServer.getGbDeviceId(), e);
            future.complete(R.fail(false, "国标28181请求回放视频流失败"));
            remoteZlmService.releaseSsrc(rtpServer.getMediaServerId(), rtpServer.getSsrc(), SecurityConstants.INNER);
            remoteZlmService.closeRTPServer(rtpServer.getMediaServerId(), rtpServer, SecurityConstants.INNER);
            sessionManager.removeByStream(rtpServer.getApp(), rtpServer.getStream());
        }

        try {
            return future.get(userSetting.getPlayTimeout().longValue(), TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("等待回放响应超时或出错 deviceId:{}", rtpServer.getGbDeviceId(), e);
            remoteZlmService.releaseSsrc(rtpServer.getMediaServerId(), rtpServer.getSsrc(), SecurityConstants.INNER);
            remoteZlmService.closeRTPServer(rtpServer.getMediaServerId(), rtpServer, SecurityConstants.INNER);
            sessionManager.removeByStream(rtpServer.getApp(), rtpServer.getStream());
            return R.fail(false, "国标28181请求回放视频流超时或出错");
        }
    }

    /**
     * 根据设备id和通道获取设备通道
     *
     * @param gbDeviceId
     * @param gbChannelId
     * @return
     */
    @GetMapping("/getDeviceChannelByChannelId/{gbDeviceId}/{gbChannelId}")
    R<DeviceChannel> getDeviceChannelByChannelId(@PathVariable String gbDeviceId, @PathVariable String gbChannelId) {
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            return R.fail("gb28181 设备不存在 deviceId:" + gbDeviceId);
        }

        DeviceChannel deviceChannel = deviceService.getDeviceChannelByChannelId(gbDeviceId, gbChannelId);

        return R.ok(deviceChannel);
    }

    /**
     * 停止视频流
     *
     * @param rtpServer
     * @return
     */
    @PostMapping("/streamByeCmd")
    R<Void> streamByeCmd(@RequestBody RtpServerParam rtpServer) {
        Device device = deviceService.getDeviceByDeviceId(rtpServer.getGbDeviceId());

        if (device == null) {
            return R.fail("gb28181 设备不存在 deviceId:" + rtpServer.getGbDeviceId());
        }

        try {
            sipCommander.stopStreamCmd(device, rtpServer);
            return R.ok();
        } catch (Exception e) {
            log.error("停止播放失败 deviceId:" + rtpServer.getGbDeviceId(), e);
            return R.fail("停止播放失败:" + e.getMessage());
        }
    }

    /**
     * 获取全部设备
     *
     * @return
     */
    @GetMapping("/getAllDevices")
    R<List<Device>> getAllDevices() {
        return R.ok(deviceService.getAllDevices());
    }

    /**
     * 通用前端控制命令(参考国标文档A.3.1指令格式)
     *
     * @param deviceId     设备国标编号
     * @param channelId    通道国标编号
     * @param cmdCode      指令码(对应国标文档指令格式中的字节4)
     * @param parameter1   数据一(对应国标文档指令格式中的字节5, 范围0-255)
     * @param parameter2   数据二(对应国标文档指令格式中的字节6, 范围0-255)
     * @param combindCode2 组合码二(对应国标文档指令格式中的字节7, 范围0-15)
     */
    @GetMapping("/common/ptz/{deviceId}/{channelId}")
    public void frontEndCommand(@PathVariable String deviceId, @PathVariable String channelId, Integer cmdCode, Integer parameter1, Integer parameter2, Integer combindCode2) {

        if (log.isDebugEnabled()) {
            log.debug(String.format("设备云台控制 API调用，deviceId：%s ，channelId：%s ，cmdCode：%d parameter1：%d parameter2：%d", deviceId, channelId, cmdCode, parameter1, parameter2));
        }

        if (parameter1 == null || parameter1 < 0 || parameter1 > 255) {
            throw new ServiceException("parameter1 为 0-255的数字");
        }
        if (parameter2 == null || parameter2 < 0 || parameter2 > 255) {
            throw new ServiceException("parameter2 为 0-255的数字");
        }
        if (combindCode2 == null || combindCode2 < 0 || combindCode2 > 15) {
            throw new ServiceException("combindCode2 为 0-15的数字");
        }

        Device device = deviceService.getDeviceByDeviceId(deviceId);

        Assert.notNull(device, "设备[" + deviceId + "]不存在");

        deviceService.frontEndCommand(device, channelId, cmdCode, parameter1, parameter2, combindCode2);
    }

    /**
     * 云台控制
     *
     * @param deviceId      设备国标编号
     * @param channelId     通道国标编号
     * @param command       控制指令,允许值: left, right, up, down, upleft, upright, downleft, downright, zoomin, zoomout, stop
     * @param horizonSpeed  水平速度(0-255)
     * @param verticalSpeed 垂直速度(0-255)
     * @param zoomSpeed     缩放速度(0-15)
     */
    @GetMapping("/ptz/{deviceId}/{channelId}")
    public void ptz(@PathVariable String deviceId, @PathVariable String channelId, String command, Integer horizonSpeed, Integer verticalSpeed, Integer zoomSpeed) {
        if (log.isDebugEnabled()) {
            log.debug(String.format("设备云台控制 API调用，deviceId：%s ，channelId：%s ，command：%s ，horizonSpeed：%d ，verticalSpeed：%d ，zoomSpeed：%d", deviceId, channelId, command, horizonSpeed, verticalSpeed, zoomSpeed));
        }
        if (horizonSpeed == null) {
            horizonSpeed = 100;
        } else if (horizonSpeed < 0 || horizonSpeed > 255) {
            throw new ServiceException("horizonSpeed 为 0-255的数字");
        }
        if (verticalSpeed == null) {
            verticalSpeed = 100;
        } else if (verticalSpeed < 0 || verticalSpeed > 255) {
            throw new ServiceException("verticalSpeed 为 0-255的数字");
        }
        if (zoomSpeed == null) {
            zoomSpeed = 16;
        } else if (zoomSpeed < 0 || zoomSpeed > 15) {
            throw new ServiceException("zoomSpeed 为 0-15的数字");
        }

        int cmdCode = 0;
        switch (command) {
            case "left":
                cmdCode = 2;
                break;
            case "right":
                cmdCode = 1;
                break;
            case "up":
                cmdCode = 8;
                break;
            case "down":
                cmdCode = 4;
                break;
            case "upleft":
                cmdCode = 10;
                break;
            case "upright":
                cmdCode = 9;
                break;
            case "downleft":
                cmdCode = 6;
                break;
            case "downright":
                cmdCode = 5;
                break;
            case "zoomin":
                cmdCode = 16;
                break;
            case "zoomout":
                cmdCode = 32;
                break;
            case "stop":
                horizonSpeed = 0;
                verticalSpeed = 0;
                zoomSpeed = 0;
                break;
            default:
                break;
        }
        frontEndCommand(deviceId, channelId, cmdCode, horizonSpeed, verticalSpeed, zoomSpeed);
    }

    /**
     * 光圈控制
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param command   控制指令,允许值: in, out, stop
     * @param speed     光圈速度(0-255)
     */
    @GetMapping("/fi/iris/{deviceId}/{channelId}")
    public void iris(@PathVariable String deviceId, @PathVariable String channelId, String command, Integer speed) {

        if (log.isDebugEnabled()) {
            log.debug("设备光圈控制 API调用，deviceId：{} ，channelId：{} ，command：{} ，speed：{} ", deviceId, channelId, command, speed);
        }

        if (speed == null) {
            speed = 100;
        } else if (speed < 0 || speed > 255) {
            throw new ServiceException("speed 为 0-255的数字");
        }

        int cmdCode = 0x40;
        switch (command) {
            case "in":
                cmdCode = 0x44;
                break;
            case "out":
                cmdCode = 0x48;
                break;
            case "stop":
                speed = 0;
                break;
            default:
                break;
        }
        frontEndCommand(deviceId, channelId, cmdCode, 0, speed, 0);
    }

    /**
     * 聚焦控制
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param command   控制指令,允许值: near, far, stop
     * @param speed     聚焦速度(0-255)
     */
    @GetMapping("/fi/focus/{deviceId}/{channelId}")
    public void focus(@PathVariable String deviceId, @PathVariable String channelId, String command, Integer speed) {

        if (log.isDebugEnabled()) {
            log.debug("设备聚焦控制 API调用，deviceId：{} ，channelId：{} ，command：{} ，speed：{} ", deviceId, channelId, command, speed);
        }

        if (speed == null) {
            speed = 100;
        } else if (speed < 0 || speed > 255) {
            throw new ServiceException("speed 为 0-255的数字");
        }

        int cmdCode = 0x40;
        switch (command) {
            case "near":
                cmdCode = 0x42;
                break;
            case "far":
                cmdCode = 0x41;
                break;
            case "stop":
                speed = 0;
                break;
            default:
                break;
        }
        frontEndCommand(deviceId, channelId, cmdCode, speed, 0, 0);
    }

    /**
     * 查询预置位
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @return
     */
    @GetMapping("/preset/query/{deviceId}/{channelId}")
    public DeferredResult<R<Object>> queryPreset(@PathVariable String deviceId, @PathVariable String channelId) {
        if (log.isDebugEnabled()) {
            log.debug("设备预置位查询API调用");
        }
        Device device = deviceService.getDeviceByDeviceId(deviceId);
        Assert.notNull(device, "设备不存在");
        DeferredResult<R<Object>> deferredResult = new DeferredResult<>(3 * 1000L);
        deviceService.queryPreset(device, channelId, (code, msg, data) -> {
            deferredResult.setResult(R.ok(data));
        });

        deferredResult.onTimeout(() -> {
            log.warn("[获取设备预置位] 超时, {}", device.getDeviceId());
            deferredResult.setResult(R.fail("获取设备预置位超时"));
        });
        return deferredResult;
    }

    /**
     * 预置位指令-设置预置位
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param presetId  预置位编号(1-255)
     */
    @GetMapping("/preset/add/{deviceId}/{channelId}")
    public void addPreset(@PathVariable String deviceId, @PathVariable String channelId, Integer presetId) {
        if (presetId == null || presetId < 1 || presetId > 255) {
            throw new ServiceException("预置位编号必须为1-255之间的数字");
        }
        frontEndCommand(deviceId, channelId, 0x81, 1, presetId, 0);
    }

    /**
     * 预置位指令-调用预置位
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param presetId  预置位编号(1-255)
     */
    @GetMapping("/preset/call/{deviceId}/{channelId}")
    public void callPreset(@PathVariable String deviceId, @PathVariable String channelId, Integer presetId) {
        if (presetId == null || presetId < 1 || presetId > 255) {
            throw new ServiceException("预置位编号必须为1-255之间的数字");
        }
        frontEndCommand(deviceId, channelId, 0x82, 1, presetId, 0);
    }

    /**
     * 预置位指令-删除预置位
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param presetId  预置位编号(1-255)
     */
    @Parameter(name = "deviceId", description = "设备国标编号", required = true)
    @Parameter(name = "channelId", description = "通道国标编号", required = true)
    @Parameter(name = "presetId", description = "预置位编号(1-255)", required = true)
    @GetMapping("/preset/delete/{deviceId}/{channelId}")
    public void deletePreset(@PathVariable String deviceId, @PathVariable String channelId, Integer presetId) {
        if (presetId == null || presetId < 1 || presetId > 255) {
            throw new ServiceException("预置位编号必须为1-255之间的数字");
        }
        frontEndCommand(deviceId, channelId, 0x83, 1, presetId, 0);
    }

    /**
     * 巡航指令-加入巡航点
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param cruiseId  巡航组号(0-255)
     * @param presetId  预置位编号(1-255)
     */
    @GetMapping("/cruise/point/add/{deviceId}/{channelId}")
    public void addCruisePoint(@PathVariable String deviceId, @PathVariable String channelId, Integer cruiseId, Integer presetId) {
        if (presetId == null || cruiseId == null || presetId < 1 || presetId > 255 || cruiseId < 0 || cruiseId > 255) {
            throw new ServiceException("编号必须为1-255之间的数字");
        }
        frontEndCommand(deviceId, channelId, 0x84, cruiseId, presetId, 0);
    }

    /**
     * 巡航指令-删除一个巡航点
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param cruiseId  巡航组号(1-255)
     * @param presetId  预置位编号(0-255, 为0时删除整个巡航)
     */
    @GetMapping("/cruise/point/delete/{deviceId}/{channelId}")
    public void deleteCruisePoint(@PathVariable String deviceId, @PathVariable String channelId, Integer cruiseId, Integer presetId) {
        if (presetId == null || presetId < 0 || presetId > 255) {
            throw new ServiceException("预置位编号必须为0-255之间的数字, 为0时删除整个巡航");
        }
        if (cruiseId == null || cruiseId < 0 || cruiseId > 255) {
            throw new ServiceException("巡航组号必须为0-255之间的数字");
        }
        frontEndCommand(deviceId, channelId, 0x85, cruiseId, presetId, 0);
    }

    /**
     * 巡航指令-设置巡航速度
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param cruiseId  巡航组号(0-255)
     * @param speed     巡航速度(1-4095)
     */
    @GetMapping("/cruise/speed/{deviceId}/{channelId}")
    public void setCruiseSpeed(@PathVariable String deviceId, @PathVariable String channelId, Integer cruiseId, Integer speed) {
        if (cruiseId == null || cruiseId < 0 || cruiseId > 255) {
            throw new ServiceException("巡航组号必须为0-255之间的数字");
        }
        if (speed == null || speed < 1 || speed > 4095) {
            throw new ServiceException("巡航速度必须为1-4095之间的数字");
        }
        int parameter2 = speed & 0xFF;
        int combindCode2 = speed >> 8;
        frontEndCommand(deviceId, channelId, 0x86, cruiseId, parameter2, combindCode2);
    }

    /**
     * 巡航指令-设置巡航停留时间
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param cruiseId  巡航组号
     * @param time      巡航停留时间(1-4095)
     */
    @GetMapping("/cruise/time/{deviceId}/{channelId}")
    public void setCruiseTime(@PathVariable String deviceId, @PathVariable String channelId, Integer cruiseId, Integer time) {
        if (cruiseId == null || cruiseId < 0 || cruiseId > 255) {
            throw new ServiceException("巡航组号必须为0-255之间的数字");
        }
        if (time == null || time < 1 || time > 4095) {
            throw new ServiceException("巡航停留时间必须为1-4095之间的数字");
        }
        int parameter2 = time & 0xFF;
        int combindCode2 = time >> 8;
        frontEndCommand(deviceId, channelId, 0x87, cruiseId, parameter2, combindCode2);
    }

    /**
     * 巡航指令-开始巡航
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param cruiseId  巡航组号
     */
    @GetMapping("/cruise/start/{deviceId}/{channelId}")
    public void startCruise(@PathVariable String deviceId, @PathVariable String channelId, Integer cruiseId) {
        if (cruiseId == null || cruiseId < 0 || cruiseId > 255) {
            throw new ServiceException("巡航组号必须为0-255之间的数字");
        }
        frontEndCommand(deviceId, channelId, 0x88, cruiseId, 0, 0);
    }

    /**
     * 巡航指令-停止巡航
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param cruiseId  巡航组号
     */
    @GetMapping("/cruise/stop/{deviceId}/{channelId}")
    public void stopCruise(@PathVariable String deviceId, @PathVariable String channelId, Integer cruiseId) {
        if (cruiseId == null || cruiseId < 0 || cruiseId > 255) {
            throw new ServiceException("巡航组号必须为0-255之间的数字");
        }
        frontEndCommand(deviceId, channelId, 0, 0, 0, 0);
    }

    /**
     * 扫描指令-开始自动扫描
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param scanId    扫描组号(0-255)
     */
    @GetMapping("/scan/start/{deviceId}/{channelId}")
    public void startScan(@PathVariable String deviceId, @PathVariable String channelId, Integer scanId) {
        if (scanId == null || scanId < 0 || scanId > 255) {
            throw new ServiceException("扫描组号必须为0-255之间的数字");
        }
        frontEndCommand(deviceId, channelId, 0x89, scanId, 0, 0);
    }

    /**
     * 扫描指令-停止自动扫描
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param scanId    扫描组号(0-255)
     */
    @GetMapping("/scan/stop/{deviceId}/{channelId}")
    public void stopScan(@PathVariable String deviceId, @PathVariable String channelId, Integer scanId) {
        if (scanId == null || scanId < 0 || scanId > 255) {
            throw new ServiceException("扫描组号必须为0-255之间的数字");
        }
        frontEndCommand(deviceId, channelId, 0, 0, 0, 0);
    }

    /**
     * 扫描指令-设置自动扫描左边界
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param scanId    扫描组号(0-255)
     */
    @GetMapping("/scan/set/left/{deviceId}/{channelId}")
    public void setScanLeft(@PathVariable String deviceId, @PathVariable String channelId, Integer scanId) {
        if (scanId == null || scanId < 0 || scanId > 255) {
            throw new ServiceException("扫描组号必须为0-255之间的数字");
        }
        frontEndCommand(deviceId, channelId, 0x89, scanId, 1, 0);
    }

    /**
     * 扫描指令-设置自动扫描右边界
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param scanId    扫描组号(0-255)
     */
    @GetMapping("/scan/set/right/{deviceId}/{channelId}")
    public void setScanRight(@PathVariable String deviceId, @PathVariable String channelId, Integer scanId) {
        if (scanId == null || scanId < 0 || scanId > 255) {
            throw new ServiceException("扫描组号必须为0-255之间的数字");
        }
        frontEndCommand(deviceId, channelId, 0x89, scanId, 2, 0);
    }

    /**
     * 扫描指令-设置自动扫描速度
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param scanId    扫描组号(0-255)
     * @param speed     自动扫描速度(1-4095)
     */
    @GetMapping("/scan/set/speed/{deviceId}/{channelId}")
    public void setScanSpeed(@PathVariable String deviceId, @PathVariable String channelId, Integer scanId, Integer speed) {
        if (scanId == null || scanId < 0 || scanId > 255) {
            throw new ServiceException("扫描组号必须为0-255之间的数字");
        }
        if (speed == null || speed < 1 || speed > 4095) {
            throw new ServiceException("自动扫描速度必须为1-4095之间的数字");
        }
        int parameter2 = speed & 0xFF;
        int combindCode2 = speed >> 8;
        frontEndCommand(deviceId, channelId, 0x8A, scanId, parameter2, combindCode2);
    }

    /**
     * 辅助开关控制指令-雨刷控制
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param command   控制指令,允许值: on, off
     */
    @GetMapping("/wiper/{deviceId}/{channelId}")
    public void wiper(@PathVariable String deviceId, @PathVariable String channelId, String command) {

        if (log.isDebugEnabled()) {
            log.debug("辅助开关控制指令-雨刷控制 API调用，deviceId：{} ，channelId：{} ，command：{}", deviceId, channelId, command);
        }

        int cmdCode = 0;
        switch (command) {
            case "on":
                cmdCode = 0x8c;
                break;
            case "off":
                cmdCode = 0x8d;
                break;
            default:
                break;
        }
        frontEndCommand(deviceId, channelId, cmdCode, 1, 0, 0);
    }

    /**
     * 辅助开关控制指令
     *
     * @param deviceId  设备国标编号
     * @param channelId 通道国标编号
     * @param command   控制指令,允许值: on, off
     * @param switchId  开关编号
     */
    @GetMapping("/auxiliary/{deviceId}/{channelId}")
    public void auxiliarySwitch(@PathVariable String deviceId, @PathVariable String channelId, String command, Integer switchId) {

        if (log.isDebugEnabled()) {
            log.debug("辅助开关控制指令-雨刷控制 API调用，deviceId:{}, channelId:{}, command:{}, switchId:{}", deviceId, channelId, command, switchId);
        }

        int cmdCode = 0;
        switch (command) {
            case "on":
                cmdCode = 0x8c;
                break;
            case "off":
                cmdCode = 0x8d;
                break;
            default:
                break;
        }
        frontEndCommand(deviceId, channelId, cmdCode, switchId, 0, 0);
    }

    /**
     * 查询录像文件列表
     */
//    @InnerAuth
    @GetMapping("/queryRecord/{deviceId}/{channelId}")
    public DeferredResult<R<RecordInfo>> queryRecord(
            @PathVariable String deviceId,
            @PathVariable String channelId,
            @RequestParam @NotBlank(message = "开始时间不能为空") String startTime,
            @RequestParam @NotBlank(message = "结束时间不能为空") String endTime) {

        if (log.isDebugEnabled()) {
            log.debug(String.format("录像信息查询 API调用，deviceId：%s ，startTime：%s， endTime：%s", deviceId, startTime, endTime));
        }
        DeferredResult<R<RecordInfo>> result = new DeferredResult<>(Long.valueOf(userSetting.getRecordInfoTimeout()), TimeUnit.MILLISECONDS);
        if (!DateUtil.verification(startTime, DateUtil.formatter)) {
            throw new ServiceException("startTime格式为" + DateUtil.PATTERN);
        }
        if (!DateUtil.verification(endTime, DateUtil.formatter)) {
            throw new ServiceException("endTime格式为" + DateUtil.PATTERN);
        }

        Device device = deviceService.getDeviceByDeviceId(deviceId);
        if (device == null) {
            throw new ServiceException(deviceId + " 不存在");
        }

        DeviceChannel channel = deviceService.getDeviceChannelByChannelId(deviceId, channelId);
        if (channel == null) {
            throw new ServiceException(channelId + " 不存在");
        }

        deviceService.queryRecord(device, channel, startTime, endTime, (code, msg, data) -> {
            R<RecordInfo> wvpResult = R.ok();
            wvpResult.setMsg(msg);
            wvpResult.setData(data);
            result.setResult(wvpResult);
        });
        result.onTimeout(() -> {
            R<RecordInfo> wvpResult = R.fail();
            wvpResult.setMsg("timeout");
            result.setResult(wvpResult);
        });
        return result;
    }

    /**
     * 查询设备配置
     *
     * @param gbDeviceId 设备国标编号
     * @param configType 配置类型，多个用/分隔
     * @return 设备配置
     */
    @GetMapping("/config/{gbDeviceId}")
    public DeferredResult<R<Object>> queryDeviceConfig(@PathVariable String gbDeviceId, @RequestParam String configType) {
        log.info("[设备配置查询API] 开始查询, 设备 ID: {}, 配置类型: {}", gbDeviceId, configType);
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            DeferredResult<R<Object>> errorResult = new DeferredResult<>();
            errorResult.setResult(R.fail("设备不存在"));
            return errorResult;
        }
        DeferredResult<R<Object>> deferredResult = new DeferredResult<>(10 * 1000L);
        log.info("[设备配置查询] 开始调用 SIP 命令, 设备 ID: {}, 配置类型: {}", device.getDeviceId(), configType);
        try {
            sipCommander.deviceConfigQuery(device, null, configType, (code, msg, data) -> {
                log.info("[设备配置查询] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
                if (code == ErrorCode.SUCCESS.getCode()) {
                    deferredResult.setResult(R.ok(data));
                } else {
                    deferredResult.setResult(R.fail(msg));
                }
            });
        } catch (Exception e) {
            log.error("[设备配置查询] 失败: {}", e.getMessage(), e);
            deferredResult.setResult(R.fail("设备配置查询失败: " + e.getMessage()));
        }

        deferredResult.onTimeout(() -> {
            log.warn("[设备配置查询] 超时, 设备 ID: {}, 配置类型: {}", gbDeviceId, configType);
            deferredResult.setResult(R.fail("获取设备配置超时"));
        });
        return deferredResult;
    }

    /**
     * 修改设备配置
     *
     * @param gbDeviceId 设备国标编号
     * @param deviceConfig 设备配置
     * @return 操作结果
     */
    @PostMapping("/config/{gbDeviceId}")
    public DeferredResult<R<Object>> updateDeviceConfig(@PathVariable String gbDeviceId, @RequestBody DeviceConfig deviceConfig) {
        log.info("[设备配置修改API] 开始修改, 设备 ID: {}", gbDeviceId);
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            DeferredResult<R<Object>> errorResult = new DeferredResult<>();
            errorResult.setResult(R.fail("设备不存在"));
            return errorResult;
        }
        DeferredResult<R<Object>> deferredResult = new DeferredResult<>(10 * 1000L);
        log.info("[设备配置修改] 开始调用 SIP 命令, 设备 ID: {}", device.getDeviceId());
        try {
            sipCommander.deviceConfigCmd(device, null, deviceConfig, (code, msg, data) -> {
                log.info("[设备配置修改] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
                if (code == ErrorCode.SUCCESS.getCode()) {
                    deferredResult.setResult(R.ok(data));
                } else {
                    deferredResult.setResult(R.fail(msg));
                }
            });
        } catch (Exception e) {
            log.error("[设备配置修改] 失败: {}", e.getMessage(), e);
            deferredResult.setResult(R.fail("设备配置修改失败: " + e.getMessage()));
        }

        deferredResult.onTimeout(() -> {
            log.warn("[设备配置修改] 超时, 设备 ID: {}", gbDeviceId);
            deferredResult.setResult(R.fail("修改设备配置超时"));
        });
        return deferredResult;
    }

    /**
     * 远程重启设备
     *
     * @param gbDeviceId  设备国标编号
     */
    @PostMapping("/reboot/{gbDeviceId}")
    public R<Void> rebootDevice(@PathVariable String gbDeviceId) {
        try {
            Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
            if (device == null) {
                return R.fail("设备不存在: " + gbDeviceId);
            }
            sipCommander.rebootDevice(device);
            return R.ok();
        } catch (Exception e) {
            log.error("[远程重启设备] 失败: {}", e.getMessage(), e);
            return R.fail("远程重启设备失败: " + e.getMessage());
        }
    }

    /**
     * 查询看守位
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @return 看守位信息
     */
    @GetMapping("/homePosition/{gbDeviceId}")
    public DeferredResult<R<Object>> queryHomePosition(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId) {
        log.info("[看守位查询API] 开始查询, 设备 ID: {}, 通道 ID: {}", gbDeviceId, channelId);
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            DeferredResult<R<Object>> errorResult = new DeferredResult<>();
            errorResult.setResult(R.fail("设备不存在"));
            return errorResult;
        }
        DeferredResult<R<Object>> deferredResult = new DeferredResult<>(10 * 1000L);
        log.info("[看守位查询] 开始调用 SIP 命令, 设备 ID: {}, 通道 ID: {}", device.getDeviceId(), channelId);
        try {
            sipCommander.homePositionQuery(device, channelId, (code, msg, data) -> {
                log.info("[看守位查询] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
                if (code == ErrorCode.SUCCESS.getCode()) {
                    deferredResult.setResult(R.ok(data));
                } else {
                    deferredResult.setResult(R.fail(msg));
                }
            });
        } catch (Exception e) {
            log.error("[看守位查询] 失败: {}", e.getMessage(), e);
            deferredResult.setResult(R.fail("看守位查询失败: " + e.getMessage()));
        }

        deferredResult.onTimeout(() -> {
            log.warn("[看守位查询] 超时, 设备 ID: {}, 通道 ID: {}", gbDeviceId, channelId);
            deferredResult.setResult(R.fail("获取看守位超时"));
        });
        return deferredResult;
    }

    /**
     * 设置看守位
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @param deviceConfig 看守位配置
     * @return 操作结果
     */
    @PostMapping("/homePosition/{gbDeviceId}")
    public DeferredResult<R<Object>> updateHomePosition(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId, @RequestBody DeviceConfig deviceConfig) {
        log.info("[看守位设置API] 开始设置, 设备 ID: {}, 通道 ID: {}", gbDeviceId, channelId);
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            DeferredResult<R<Object>> errorResult = new DeferredResult<>();
            errorResult.setResult(R.fail("设备不存在"));
            return errorResult;
        }
        DeferredResult<R<Object>> deferredResult = new DeferredResult<>(10 * 1000L);
        log.info("[看守位设置] 开始调用 SIP 命令, 设备 ID: {}, 通道 ID: {}", device.getDeviceId(), channelId);
        try {
            sipCommander.homePositionCmd(device, channelId, deviceConfig, (code, msg, data) -> {
                log.info("[看守位设置] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
                if (code == ErrorCode.SUCCESS.getCode()) {
                    deferredResult.setResult(R.ok(data));
                } else {
                    deferredResult.setResult(R.fail(msg));
                }
            });
        } catch (Exception e) {
            log.error("[看守位设置] 失败: {}", e.getMessage(), e);
            deferredResult.setResult(R.fail("看守位设置失败: " + e.getMessage()));
        }

        deferredResult.onTimeout(() -> {
            log.warn("[看守位设置] 超时, 设备 ID: {}, 通道 ID: {}", gbDeviceId, channelId);
            deferredResult.setResult(R.fail("设置看守位超时"));
        });
        return deferredResult;
    }

    /**
     * 查询巡航轨迹列表
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @return 巡航轨迹列表
     */
    @GetMapping("/cruiseTrackList/{gbDeviceId}")
    public DeferredResult<R<Object>> queryCruiseTrackList(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId) {
        log.info("[巡航轨迹列表查询API] 开始查询, 设备 ID: {}, 通道 ID: {}", gbDeviceId, channelId);
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            DeferredResult<R<Object>> errorResult = new DeferredResult<>();
            errorResult.setResult(R.fail("设备不存在"));
            return errorResult;
        }
        DeferredResult<R<Object>> deferredResult = new DeferredResult<>(10 * 1000L);
        log.info("[巡航轨迹列表查询] 开始调用 SIP 命令, 设备 ID: {}, 通道 ID: {}", device.getDeviceId(), channelId);
        try {
            sipCommander.cruiseTrackListQuery(device, channelId, (code, msg, data) -> {
                log.info("[巡航轨迹列表查询] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
                if (code == ErrorCode.SUCCESS.getCode()) {
                    deferredResult.setResult(R.ok(data));
                } else {
                    deferredResult.setResult(R.fail(msg));
                }
            });
        } catch (Exception e) {
            log.error("[巡航轨迹列表查询] 失败: {}", e.getMessage(), e);
            deferredResult.setResult(R.fail("巡航轨迹列表查询失败: " + e.getMessage()));
        }

        deferredResult.onTimeout(() -> {
            log.warn("[巡航轨迹列表查询] 超时, 设备 ID: {}, 通道 ID: {}", gbDeviceId, channelId);
            deferredResult.setResult(R.fail("查询巡航轨迹列表超时"));
        });
        return deferredResult;
    }

    /**
     * 查询巡航轨迹
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @param number 轨迹编号：0-第一条轨迹，1-第二条轨迹
     * @return 巡航轨迹信息
     */
    @GetMapping("/cruiseTrack/{gbDeviceId}")
    public DeferredResult<R<Object>> queryCruiseTrack(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId, @RequestParam Integer number) {
        log.info("[巡航轨迹查询API] 开始查询, 设备 ID: {}, 通道 ID: {}, 轨迹编号: {}", gbDeviceId, channelId, number);
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            DeferredResult<R<Object>> errorResult = new DeferredResult<>();
            errorResult.setResult(R.fail("设备不存在"));
            return errorResult;
        }
        DeferredResult<R<Object>> deferredResult = new DeferredResult<>(10 * 1000L);
        log.info("[巡航轨迹查询] 开始调用 SIP 命令, 设备 ID: {}, 通道 ID: {}, 轨迹编号: {}", device.getDeviceId(), channelId, number);
        try {
            sipCommander.cruiseTrackQuery(device, channelId, number, (code, msg, data) -> {
                log.info("[巡航轨迹查询] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
                if (code == ErrorCode.SUCCESS.getCode()) {
                    deferredResult.setResult(R.ok(data));
                } else {
                    deferredResult.setResult(R.fail(msg));
                }
            });
        } catch (Exception e) {
            log.error("[巡航轨迹查询] 失败: {}", e.getMessage(), e);
            deferredResult.setResult(R.fail("巡航轨迹查询失败: " + e.getMessage()));
        }

        deferredResult.onTimeout(() -> {
            log.warn("[巡航轨迹查询] 超时, 设备 ID: {}, 通道 ID: {}, 轨迹编号: {}", gbDeviceId, channelId, number);
            deferredResult.setResult(R.fail("查询巡航轨迹超时"));
        });
        return deferredResult;
    }

    /**
     * PTZ精准状态查询
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @return PTZ精准状态信息
     */
    @GetMapping("/ptzPosition/{gbDeviceId}")
    public DeferredResult<R<Object>> queryPTZPosition(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId) {
        log.info("[PTZ精准状态查询API] 开始查询, 设备 ID: {}, 通道 ID: {}", gbDeviceId, channelId);
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            DeferredResult<R<Object>> errorResult = new DeferredResult<>();
            errorResult.setResult(R.fail("设备不存在"));
            return errorResult;
        }
        DeferredResult<R<Object>> deferredResult = new DeferredResult<>(10 * 1000L);
        log.info("[PTZ精准状态查询] 开始调用 SIP 命令, 设备 ID: {}, 通道 ID: {}", device.getDeviceId(), channelId);
        try {
            sipCommander.ptzPositionQuery(device, channelId, (code, msg, data) -> {
                log.info("[PTZ精准状态查询] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
                if (code == ErrorCode.SUCCESS.getCode()) {
                    deferredResult.setResult(R.ok(data));
                } else {
                    deferredResult.setResult(R.fail(msg));
                }
            });
        } catch (Exception e) {
            log.error("[PTZ精准状态查询] 失败: {}", e.getMessage(), e);
            deferredResult.setResult(R.fail("PTZ精准状态查询失败: " + e.getMessage()));
        }

        deferredResult.onTimeout(() -> {
            log.warn("[PTZ精准状态查询] 超时, 设备 ID: {}, 通道 ID: {}", gbDeviceId, channelId);
            deferredResult.setResult(R.fail("查询PTZ精准状态超时"));
        });
        return deferredResult;
    }

    /**
     * 存储卡状态查询
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @return 存储卡状态信息
     */
    @GetMapping("/sdCardStatus/{gbDeviceId}")
    public DeferredResult<R<Object>> querySDCardStatus(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId) {
        log.info("[存储卡状态查询API] 开始查询, 设备 ID: {}, 通道 ID: {}", gbDeviceId, channelId);
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            DeferredResult<R<Object>> errorResult = new DeferredResult<>();
            errorResult.setResult(R.fail("设备不存在"));
            return errorResult;
        }
        DeferredResult<R<Object>> deferredResult = new DeferredResult<>(10 * 1000L);
        log.info("[存储卡状态查询] 开始调用 SIP 命令, 设备 ID: {}, 通道 ID: {}", device.getDeviceId(), channelId);
        try {
            sipCommander.sdCardStatusQuery(device, channelId, (code, msg, data) -> {
                log.info("[存储卡状态查询] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
                if (code == ErrorCode.SUCCESS.getCode()) {
                    deferredResult.setResult(R.ok(data));
                } else {
                    deferredResult.setResult(R.fail(msg));
                }
            });
        } catch (Exception e) {
            log.error("[存储卡状态查询] 失败: {}", e.getMessage(), e);
            deferredResult.setResult(R.fail("存储卡状态查询失败: " + e.getMessage()));
        }

        deferredResult.onTimeout(() -> {
            log.warn("[存储卡状态查询] 超时, 设备 ID: {}, 通道 ID: {}", gbDeviceId, channelId);
            deferredResult.setResult(R.fail("查询存储卡状态超时"));
        });
        return deferredResult;
    }

    /**
     * 报警复位控制
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @param alarmMethod 报警方式（可选），0-全部，1-电话报警，2-设备报警，3-短信报警，4-GPS报警，5-视频报警，6-设备故障报警，7-其他报警
     * @param alarmType 报警类型（可选）
     * @return 报警复位控制结果
     */
    @PostMapping("/alarmReset/{gbDeviceId}")
    public DeferredResult<R<Object>> alarmResetControl(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId, @RequestParam(required = false) String alarmMethod, @RequestParam(required = false) String alarmType) {
        log.info("[报警复位控制API] 开始控制, 设备 ID: {}, 通道 ID: {}, 报警方式: {}, 报警类型: {}", gbDeviceId, channelId, alarmMethod, alarmType);
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            DeferredResult<R<Object>> errorResult = new DeferredResult<>();
            errorResult.setResult(R.fail("设备不存在"));
            return errorResult;
        }
        DeferredResult<R<Object>> deferredResult = new DeferredResult<>(10 * 1000L);
        log.info("[报警复位控制] 开始调用 SIP 命令, 设备 ID: {}, 通道 ID: {}, 报警方式: {}, 报警类型: {}", device.getDeviceId(), channelId, alarmMethod, alarmType);
        try {
            sipCommander.alarmResetControl(device, channelId, alarmMethod, alarmType, (code, msg, data) -> {
                log.info("[报警复位控制] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
                if (code == ErrorCode.SUCCESS.getCode()) {
                    deferredResult.setResult(R.ok(data));
                } else {
                    deferredResult.setResult(R.fail(msg));
                }
            });
        } catch (Exception e) {
            log.error("[报警复位控制] 失败: {}", e.getMessage(), e);
            deferredResult.setResult(R.fail("报警复位控制失败: " + e.getMessage()));
        }

        deferredResult.onTimeout(() -> {
            log.warn("[报警复位控制] 超时, 设备 ID: {}, 通道 ID: {}, 报警方式: {}, 报警类型: {}", gbDeviceId, channelId, alarmMethod, alarmType);
            deferredResult.setResult(R.fail("报警复位控制超时"));
        });
        return deferredResult;
    }

    /**
     * 强制关键帧控制
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @return 强制关键帧控制结果
     */
    @PostMapping("/iFrame/{gbDeviceId}")
    public DeferredResult<R<Object>> iFrameControl(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId) {
        log.info("[强制关键帧控制API] 开始控制, 设备 ID: {}, 通道 ID: {}", gbDeviceId, channelId);
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            DeferredResult<R<Object>> errorResult = new DeferredResult<>();
            errorResult.setResult(R.fail("设备不存在"));
            return errorResult;
        }
        DeferredResult<R<Object>> deferredResult = new DeferredResult<>(10 * 1000L);
        log.info("[强制关键帧控制] 开始调用 SIP 命令, 设备 ID: {}, 通道 ID: {}", device.getDeviceId(), channelId);
        try {
            sipCommander.iFrameControl(device, channelId, (code, msg, data) -> {
                log.info("[强制关键帧控制] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
                if (code == ErrorCode.SUCCESS.getCode()) {
                    deferredResult.setResult(R.ok(data));
                } else {
                    deferredResult.setResult(R.fail(msg));
                }
            });
        } catch (Exception e) {
            log.error("[强制关键帧控制] 失败: {}", e.getMessage(), e);
            deferredResult.setResult(R.fail("强制关键帧控制失败: " + e.getMessage()));
        }

        deferredResult.onTimeout(() -> {
            log.warn("[强制关键帧控制] 超时, 设备 ID: {}, 通道 ID: {}", gbDeviceId, channelId);
            deferredResult.setResult(R.fail("强制关键帧控制超时"));
        });
        return deferredResult;
    }

    /**
     * 看守位控制
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @param deviceConfig 设备配置，包含看守位配置
     * @return 看守位控制结果
     */
    @PostMapping("/homePosition/control/{gbDeviceId}")
    public DeferredResult<R<Object>> homePositionControl(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId, @RequestBody DeviceConfig deviceConfig) {
        log.info("[看守位控制API] 开始控制, 设备 ID: {}, 通道 ID: {}, 配置: {}", gbDeviceId, channelId, deviceConfig != null ? deviceConfig.getHomePosition() : null);
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            DeferredResult<R<Object>> errorResult = new DeferredResult<>();
            errorResult.setResult(R.fail("设备不存在"));
            return errorResult;
        }
        DeferredResult<R<Object>> deferredResult = new DeferredResult<>(10 * 1000L);
        log.info("[看守位控制] 开始调用 SIP 命令, 设备 ID: {}, 通道 ID: {}, 配置: {}", device.getDeviceId(), channelId, deviceConfig != null ? deviceConfig.getHomePosition() : null);
        try {
            sipCommander.homePositionControl(device, channelId, deviceConfig, (code, msg, data) -> {
                log.info("[看守位控制] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
                if (code == ErrorCode.SUCCESS.getCode()) {
                    deferredResult.setResult(R.ok(data));
                } else {
                    deferredResult.setResult(R.fail(msg));
                }
            });
        } catch (Exception e) {
            log.error("[看守位控制] 失败: {}", e.getMessage(), e);
            deferredResult.setResult(R.fail("看守位控制失败: " + e.getMessage()));
        }

        deferredResult.onTimeout(() -> {
            log.warn("[看守位控制] 超时, 设备 ID: {}, 通道 ID: {}, 配置: {}", gbDeviceId, channelId, deviceConfig != null ? deviceConfig.getHomePosition() : null);
            deferredResult.setResult(R.fail("看守位控制超时"));
        });
        return deferredResult;
    }

    /**
     * PTZ精准控制
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @param ptzPreciseCtrl PTZ精准控制参数
     * @return PTZ精准控制结果
     */
    @PostMapping("/ptzPrecise/{gbDeviceId}")
    public DeferredResult<R<Object>> ptzPreciseControl(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId, @RequestBody JSONObject ptzPreciseCtrl) {
        log.info("[PTZ精准控制API] 开始控制, 设备 ID: {}, 通道 ID: {}, 参数: {}", gbDeviceId, channelId, ptzPreciseCtrl);
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            DeferredResult<R<Object>> errorResult = new DeferredResult<>();
            errorResult.setResult(R.fail("设备不存在"));
            return errorResult;
        }
        DeferredResult<R<Object>> deferredResult = new DeferredResult<>(10 * 1000L);
        log.info("[PTZ精准控制] 开始调用 SIP 命令, 设备 ID: {}, 通道 ID: {}, 参数: {}", device.getDeviceId(), channelId, ptzPreciseCtrl);
        try {
            sipCommander.ptzPreciseControl(device, channelId, ptzPreciseCtrl, (code, msg, data) -> {
                log.info("[PTZ精准控制] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
                if (code == ErrorCode.SUCCESS.getCode()) {
                    deferredResult.setResult(R.ok(data));
                } else {
                    deferredResult.setResult(R.fail(msg));
                }
            });
        } catch (Exception e) {
            log.error("[PTZ精准控制] 失败: {}", e.getMessage(), e);
            deferredResult.setResult(R.fail("PTZ精准控制失败: " + e.getMessage()));
        }

        deferredResult.onTimeout(() -> {
            log.warn("[PTZ精准控制] 超时, 设备 ID: {}, 通道 ID: {}, 参数: {}", gbDeviceId, channelId, ptzPreciseCtrl);
            deferredResult.setResult(R.fail("PTZ精准控制超时"));
        });
        return deferredResult;
    }

    /**
     * 设备软件升级控制
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @param firmware 设备固件版本
     * @param fileURL 升级文件的完整路径
     * @param manufacturer 设备厂商
     * @param sessionID 会话ID
     * @return 设备软件升级控制结果
     */
    @PostMapping("/deviceUpgrade/{gbDeviceId}")
    public DeferredResult<R<Object>> deviceUpgradeControl(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId,
        @RequestParam String firmware, @RequestParam String fileURL, @RequestParam String manufacturer,
        @RequestParam String sessionID) {
        log.info("[设备软件升级控制API] 开始控制, 设备 ID: {}, 通道 ID: {}, 固件版本: {}, 文件URL: {}, 厂商: {}, 会话ID: {}", 
            gbDeviceId, channelId, firmware, fileURL, manufacturer, sessionID);
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            DeferredResult<R<Object>> errorResult = new DeferredResult<>();
            errorResult.setResult(R.fail("设备不存在"));
            return errorResult;
        }
        DeferredResult<R<Object>> deferredResult = new DeferredResult<>(10 * 1000L);
        log.info("[设备软件升级控制] 开始调用 SIP 命令, 设备 ID: {}, 通道 ID: {}, 固件版本: {}, 文件URL: {}, 厂商: {}, 会话ID: {}", 
            device.getDeviceId(), channelId, firmware, fileURL, manufacturer, sessionID);
        try {
            sipCommander.deviceUpgradeControl(device, channelId, firmware, fileURL, manufacturer, sessionID, (code, msg, data) -> {
                log.info("[设备软件升级控制] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
                if (code == ErrorCode.SUCCESS.getCode()) {
                    deferredResult.setResult(R.ok(data));
                } else {
                    deferredResult.setResult(R.fail(msg));
                }
            });
        } catch (Exception e) {
            log.error("[设备软件升级控制] 失败: {}", e.getMessage(), e);
            deferredResult.setResult(R.fail("设备软件升级控制失败: " + e.getMessage()));
        }

        deferredResult.onTimeout(() -> {
            log.warn("[设备软件升级控制] 超时, 设备 ID: {}, 通道 ID: {}, 固件版本: {}, 文件URL: {}, 厂商: {}, 会话ID: {}", 
                gbDeviceId, channelId, firmware, fileURL, manufacturer, sessionID);
            deferredResult.setResult(R.fail("设备软件升级控制超时"));
        });
        return deferredResult;
    }

    /**
     * 存储卡格式化控制
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @param sdCardId SD卡编号（0表示所有存储卡）
     * @return 存储卡格式化控制结果
     */
    @PostMapping("/formatSDCard/{gbDeviceId}")
    public DeferredResult<R<Object>> formatSDCardControl(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId,
        @RequestParam Integer sdCardId) {
        log.info("[存储卡格式化控制API] 开始控制, 设备 ID: {}, 通道 ID: {}, SD卡编号: {}", 
            gbDeviceId, channelId, sdCardId);
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            DeferredResult<R<Object>> errorResult = new DeferredResult<>();
            errorResult.setResult(R.fail("设备不存在"));
            return errorResult;
        }
        DeferredResult<R<Object>> deferredResult = new DeferredResult<>(10 * 1000L);
        log.info("[存储卡格式化控制] 开始调用 SIP 命令, 设备 ID: {}, 通道 ID: {}, SD卡编号: {}", 
            device.getDeviceId(), channelId, sdCardId);
        try {
            sipCommander.formatSDCardControl(device, channelId, sdCardId, (code, msg, data) -> {
                log.info("[存储卡格式化控制] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
                if (code == ErrorCode.SUCCESS.getCode()) {
                    deferredResult.setResult(R.ok(data));
                } else {
                    deferredResult.setResult(R.fail(msg));
                }
            });
        } catch (Exception e) {
            log.error("[存储卡格式化控制] 失败: {}", e.getMessage(), e);
            deferredResult.setResult(R.fail("存储卡格式化控制失败: " + e.getMessage()));
        }

        deferredResult.onTimeout(() -> {
            log.warn("[存储卡格式化控制] 超时, 设备 ID: {}, 通道 ID: {}, SD卡编号: {}", 
                gbDeviceId, channelId, sdCardId);
            deferredResult.setResult(R.fail("存储卡格式化控制超时"));
        });
        return deferredResult;
    }

    /**
     * 目标跟踪控制
     *
     * @param gbDeviceId 设备国标编号（球机通道）
     * @param channelId 通道国标编号（可选，指球机通道）
     * @param targetTrack 跟踪类型：Auto/Manual/Stop
     * @param deviceId2 目标设备编码（可选，指全景相机中的全景通道ID）
     * @param targetArea 目标区域（可选，手动跟踪时需要）
     * @return 目标跟踪控制结果
     */
    @PostMapping("/targetTrack/{gbDeviceId}")
    public DeferredResult<R<Object>> targetTrackControl(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId,
        @RequestParam String targetTrack, @RequestParam(required = false) String deviceId2,
        @RequestBody(required = false) JSONObject targetArea) {
        log.info("[目标跟踪控制API] 开始控制, 设备 ID: {}, 通道 ID: {}, 跟踪类型: {}, 全景通道 ID: {}, 目标区域: {}", 
            gbDeviceId, channelId, targetTrack, deviceId2, targetArea);
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            DeferredResult<R<Object>> errorResult = new DeferredResult<>();
            errorResult.setResult(R.fail("设备不存在"));
            return errorResult;
        }
        DeferredResult<R<Object>> deferredResult = new DeferredResult<>(10 * 1000L);
        log.info("[目标跟踪控制] 开始调用 SIP 命令, 设备 ID: {}, 通道 ID: {}, 跟踪类型: {}, 全景通道 ID: {}, 目标区域: {}", 
            device.getDeviceId(), channelId, targetTrack, deviceId2, targetArea);
        try {
            sipCommander.targetTrackControl(device, channelId, targetTrack, deviceId2, targetArea, (code, msg, data) -> {
                log.info("[目标跟踪控制] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
                if (code == ErrorCode.SUCCESS.getCode()) {
                    deferredResult.setResult(R.ok(data));
                } else {
                    deferredResult.setResult(R.fail(msg));
                }
            });
        } catch (Exception e) {
            log.error("[目标跟踪控制] 失败: {}", e.getMessage(), e);
            deferredResult.setResult(R.fail("目标跟踪控制失败: " + e.getMessage()));
        }

        deferredResult.onTimeout(() -> {
            log.warn("[目标跟踪控制] 超时, 设备 ID: {}, 通道 ID: {}, 跟踪类型: {}, 全景通道 ID: {}, 目标区域: {}", 
                gbDeviceId, channelId, targetTrack, deviceId2, targetArea);
            deferredResult.setResult(R.fail("目标跟踪控制超时"));
        });
        return deferredResult;
    }

    /**
     * 抓图控制并保存
     *
     * @param gbDeviceId 设备国标编号
     * @param channelId 通道国标编号（可选）
     * @param snapshotType 抓图类型（可选）
     * @return 抓图记录ID
     */
    @PostMapping("/captureAndSave/{gbDeviceId}")
    public DeferredResult<R<Long>> captureAndSave(@PathVariable String gbDeviceId, @RequestParam(required = false) String channelId,
        @RequestParam(required = false) String snapshotType) {
        log.info("[抓图控制API] 开始抓图, 设备 ID: {}, 通道 ID: {}, 抓图类型: {}", gbDeviceId, channelId, snapshotType);
        
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            DeferredResult<R<Long>> errorResult = new DeferredResult<>();
            errorResult.setResult(R.fail("设备不存在"));
            return errorResult;
        }
        
        // 获取对应的QsDevice
        QsDevice qsDevice = null;
        try {
            R<QsDevice> qsDeviceR = remoteQsDeviceService.getDeviceByGbCode(gbDeviceId, SecurityConstants.INNER);
            if (qsDeviceR.getCode() == Constants.SUCCESS) {
                qsDevice = qsDeviceR.getData();
            }
        } catch (Exception e) {
            log.warn("[抓图控制] 获取QsDevice失败: {}", e.getMessage());
        }
        
        DeferredResult<R<Long>> deferredResult = new DeferredResult<>(10 * 1000L);
        
        try {
            QsDevice finalQsDevice = qsDevice;
            sipCommander.pictureCaptureControl(device, channelId, (code, msg, data) -> {
                log.info("[抓图控制] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
                
                if (code == ErrorCode.SUCCESS.getCode()) {
                    try {
                        // 创建抓图记录
                        QsDeviceSnapshot snapshot = new QsDeviceSnapshot();
                        if (finalQsDevice != null) {
                            snapshot.setDeviceId(finalQsDevice.getId());
                            snapshot.setDeviceCode(finalQsDevice.getDeviceCode());
                            snapshot.setDeviceName(finalQsDevice.getDeviceName());
                            snapshot.setChannel(finalQsDevice.getGbChannelId() != null ? Long.parseLong(finalQsDevice.getGbChannelId()) : null);
                        }
                        
                        // 生成文件名和路径
                        String timestamp = String.valueOf(System.currentTimeMillis());
                        String saveDir = filePath + "/gb28181/snapshot/" + gbDeviceId + "/" + timestamp;
                        File dir = new File(saveDir);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        
                        String fileName = "snapshot_" + gbDeviceId + "_" + (channelId != null ? channelId : "0") + "_" + 
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".jpg";
                        String localFilePath = saveDir + File.separator + fileName;
                        String fileUrl = fileDomain + filePrefix + "/gb28181/snapshot/" + gbDeviceId + "/" + timestamp + "/" + fileName;
                        
                        snapshot.setFileName(fileName);
                        snapshot.setFilePath(localFilePath);
                        snapshot.setFileUrl(fileUrl);
                        snapshot.setFileType("jpg");
                        snapshot.setSdkType("gb28181");
                        snapshot.setSnapshotType(snapshotType != null ? snapshotType : "manual");
                        snapshot.setCaptureTime(new Date());
                        
                        // 保存到数据库
                        R<Long> saveResult = remoteQsDeviceSnapshotService.add(snapshot, SecurityConstants.INNER);
                        
                        if (saveResult.getCode() == Constants.SUCCESS) {
                            log.info("[抓图控制] 抓图记录保存成功, ID: {}", saveResult.getData());
                            deferredResult.setResult(R.ok(saveResult.getData()));
                        } else {
                            log.error("[抓图控制] 抓图记录保存失败: {}", saveResult.getMsg());
                            deferredResult.setResult(R.fail("保存抓图记录失败: " + saveResult.getMsg()));
                        }
                    } catch (Exception e) {
                        log.error("[抓图控制] 保存抓图记录异常: {}", e.getMessage(), e);
                        deferredResult.setResult(R.fail("保存抓图记录异常: " + e.getMessage()));
                    }
                } else {
                    log.error("[抓图控制] 抓图命令失败: {}", msg);
                    deferredResult.setResult(R.fail("抓图失败: " + msg));
                }
            });
        } catch (Exception e) {
            log.error("[抓图控制] 失败: {}", e.getMessage(), e);
            deferredResult.setResult(R.fail("抓图失败: " + e.getMessage()));
        }
        
        deferredResult.onTimeout(() -> {
            log.warn("[抓图控制] 超时, 设备 ID: {}, 通道 ID: {}", gbDeviceId, channelId);
            deferredResult.setResult(R.fail("抓图超时"));
        });
        
        return deferredResult;
    }

    /**
     * 设备校时控制
     *
     * @param gbDeviceId 设备国标编号
     * @return 校时结果
     */
    @PostMapping("/timeCheck/{gbDeviceId}")
    public DeferredResult<R<Object>> timeCheckCmd(@PathVariable String gbDeviceId) {
        log.info("[校时控制API] 开始校时, 设备 ID: {}", gbDeviceId);
        
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            DeferredResult<R<Object>> errorResult = new DeferredResult<>();
            errorResult.setResult(R.fail("设备不存在"));
            return errorResult;
        }
        
        DeferredResult<R<Object>> deferredResult = new DeferredResult<>(10 * 1000L);
        
        log.info("[校时控制] 开始调用 SIP 命令, 设备 ID: {}", device.getDeviceId());
        try {
            sipCommander.timeCheckCmd(device, (code, msg, data) -> {
                log.info("[校时控制] 收到回调, code: {}, msg: {}, data: {}", code, msg, data);
                if (code == ErrorCode.SUCCESS.getCode()) {
                    deferredResult.setResult(R.ok(data));
                } else {
                    deferredResult.setResult(R.fail(msg));
                }
            });
        } catch (Exception e) {
            log.error("[校时控制] 失败: {}", e.getMessage(), e);
            deferredResult.setResult(R.fail("校时失败: " + e.getMessage()));
        }
        
        deferredResult.onTimeout(() -> {
            log.warn("[校时控制] 超时, 设备 ID: {}", gbDeviceId);
            deferredResult.setResult(R.fail("校时超时"));
        });
        
        return deferredResult;
    }

}
