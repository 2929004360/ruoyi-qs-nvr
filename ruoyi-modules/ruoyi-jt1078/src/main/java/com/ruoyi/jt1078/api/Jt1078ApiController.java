package com.ruoyi.jt1078.api;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.common.security.annotation.InnerAuth;
import com.ruoyi.jt1078.api.domain.Jt1078Device;
import com.ruoyi.jt1078.protocol.t1078.*;
import com.ruoyi.jt1078.server.endpoint.MessageManager;
import com.ruoyi.jt1078.server.model.entity.DeviceDO;
import com.ruoyi.jt1078.server.service.IRedisCatchStorage;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/jt1078")
@RequiredArgsConstructor
public class Jt1078ApiController {

    private final IRedisCatchStorage redisCatchStorage;

    private final MessageManager messageManager;

    private static final DateTimeFormatter BCD_FORMATTER = DateTimeFormatter.ofPattern("yyMMddHHmmss");

    @GetMapping("/getDeviceByMobileNo/{mobileNo}")
    public R<Jt1078Device> getDeviceByMobileNo(@PathVariable String mobileNo) {
        DeviceDO deviceDO = redisCatchStorage.getDevice(mobileNo);
        if (deviceDO == null) {
            return R.fail("jt1078 设备不存在 mobileNo:" + mobileNo);
        }

        Jt1078Device jt1078Device = new Jt1078Device();
        BeanUtils.copyProperties(deviceDO, jt1078Device);

        return R.ok(jt1078Device);
    }

    @PostMapping("/playStreamCmd")
    public R<Void> playStreamCmd(@RequestBody RtpServerParam rtpServer) {
        log.info("[JT1078 播放请求] rtpServer:{}", rtpServer);

        DeviceDO deviceDO = redisCatchStorage.getDevice(rtpServer.getMobileNo());
        if (deviceDO == null) {
            return R.fail("jt1078 设备不存在 mobileNo:" + rtpServer.getMobileNo());
        }

        Integer channelNo = rtpServer.getChannel() != null ? rtpServer.getChannel() : 1;
        
        T9101 t9101 = new T9101()
                .setIp(rtpServer.getIp())
                .setTcpPort(rtpServer.getPort())
                .setUdpPort(rtpServer.getPort())
                .setChannelNo(channelNo)
                .setMediaType(0)
                .setStreamType(0);
        t9101.setClientId(deviceDO.getMobileNo());

        try {
            messageManager.notify(deviceDO.getMobileNo(), t9101).block();
            log.info("[JT1078 播放请求成功] mobileNo:{}", deviceDO.getMobileNo());
            return R.ok();
        } catch (Exception e) {
            log.error("[JT1078 播放请求失败] mobileNo:{}", deviceDO.getMobileNo(), e);
            return R.fail("jt1078 播放请求失败:" + e.getMessage());
        }
    }

    @PostMapping("/streamByeCmd")
    public R<Void> streamByeCmd(@RequestBody RtpServerParam rtpServer) {
        log.info("[JT1078 停止播放请求] rtpServer:{}", rtpServer);

        DeviceDO deviceDO = redisCatchStorage.getDevice(rtpServer.getMobileNo());
        if (deviceDO == null) {
            return R.fail("jt1078 设备不存在 mobileNo:" + rtpServer.getMobileNo());
        }

        Integer channelNo = rtpServer.getChannel() != null ? rtpServer.getChannel() : 1;
        
        T9102 t9102 = new T9102()
                .setChannelNo(channelNo)
                .setCommand(0)
                .setCloseType(0);
        t9102.setClientId(deviceDO.getMobileNo());

        try {
            messageManager.notify(deviceDO.getMobileNo(), t9102).block();
            log.info("[JT1078 停止播放请求成功] mobileNo:{}", deviceDO.getMobileNo());
            return R.ok();
        } catch (Exception e) {
            log.error("[JT1078 停止播放请求失败] mobileNo:{}", deviceDO.getMobileNo(), e);
            return R.fail("jt1078 停止播放请求失败:" + e.getMessage());
        }
    }

    /**
     * 获取全部设备
     *
     * @return
     */
    @GetMapping("/getAllDevices")
    R<List<Jt1078Device>> getAllDevices() {
        List<DeviceDO> deviceDOList = redisCatchStorage.getAllDevice();
        List<Jt1078Device> deviceList = deviceDOList.stream()
                .map(deviceDO -> {
                    Jt1078Device device = new Jt1078Device();
                    BeanUtils.copyProperties(deviceDO, device);
                    return device;
                })
                .collect(Collectors.toList());
        return R.ok(deviceList);
    }

    /**
     * 云台旋转
     */
    @GetMapping("/ptzRotate/{mobileNo}/{channelNo}")
    public R<Void> ptzRotate(@PathVariable String mobileNo, @PathVariable int channelNo,
                             @RequestParam int direction, @RequestParam(defaultValue = "50") int speed) {
        log.info("[JT1078 云台旋转] mobileNo:{}, channelNo:{}, direction:{}, speed:{}", mobileNo, channelNo, direction, speed);

        DeviceDO deviceDO = redisCatchStorage.getDevice(mobileNo);
        if (deviceDO == null) {
            return R.fail("jt1078 设备不存在 mobileNo:" + mobileNo);
        }

        T9301 t9301 = new T9301()
                .setChannelNo(channelNo)
                .setDirection(direction)
                .setSpeed(speed);
        t9301.setClientId(mobileNo);

        try {
            messageManager.notify(mobileNo, t9301).block();
            log.info("[JT1078 云台旋转成功] mobileNo:{}", mobileNo);
            return R.ok();
        } catch (Exception e) {
            log.error("[JT1078 云台旋转失败] mobileNo:{}", mobileNo, e);
            return R.fail("jt1078 云台旋转失败:" + e.getMessage());
        }
    }

    /**
     * 云台调整焦距控制
     */
    @GetMapping("/ptzFocus/{mobileNo}/{channelNo}")
    public R<Void> ptzFocus(@PathVariable String mobileNo, @PathVariable int channelNo,
                            @RequestParam int direction, @RequestParam(defaultValue = "50") int speed) {
        log.info("[JT1078 云台调整焦距] mobileNo:{}, channelNo:{}, direction:{}, speed:{}", mobileNo, channelNo, direction, speed);

        DeviceDO deviceDO = redisCatchStorage.getDevice(mobileNo);
        if (deviceDO == null) {
            return R.fail("jt1078 设备不存在 mobileNo:" + mobileNo);
        }

        T9302 t9302 = new T9302()
                .setChannelNo(channelNo)
                .setDirection(direction)
                .setSpeed(speed);
        t9302.setClientId(mobileNo);

        try {
            messageManager.notify(mobileNo, t9302).block();
            log.info("[JT1078 云台调整焦距成功] mobileNo:{}", mobileNo);
            return R.ok();
        } catch (Exception e) {
            log.error("[JT1078 云台调整焦距失败] mobileNo:{}", mobileNo, e);
            return R.fail("jt1078 云台调整焦距失败:" + e.getMessage());
        }
    }

    /**
     * 云台调整光圈控制
     */
    @GetMapping("/ptzIris/{mobileNo}/{channelNo}")
    public R<Void> ptzIris(@PathVariable String mobileNo, @PathVariable int channelNo,
                           @RequestParam int direction, @RequestParam(defaultValue = "50") int speed) {
        log.info("[JT1078 云台调整光圈] mobileNo:{}, channelNo:{}, direction:{}, speed:{}", mobileNo, channelNo, direction, speed);

        DeviceDO deviceDO = redisCatchStorage.getDevice(mobileNo);
        if (deviceDO == null) {
            return R.fail("jt1078 设备不存在 mobileNo:" + mobileNo);
        }

        T9303 t9303 = new T9303()
                .setChannelNo(channelNo)
                .setDirection(direction)
                .setSpeed(speed);
        t9303.setClientId(mobileNo);

        try {
            messageManager.notify(mobileNo, t9303).block();
            log.info("[JT1078 云台调整光圈成功] mobileNo:{}", mobileNo);
            return R.ok();
        } catch (Exception e) {
            log.error("[JT1078 云台调整光圈失败] mobileNo:{}", mobileNo, e);
            return R.fail("jt1078 云台调整光圈失败:" + e.getMessage());
        }
    }

    /**
     * 云台雨刷控制
     */
    @GetMapping("/ptzWiper/{mobileNo}/{channelNo}")
    public R<Void> ptzWiper(@PathVariable String mobileNo, @PathVariable int channelNo,
                            @RequestParam int control) {
        log.info("[JT1078 云台雨刷控制] mobileNo:{}, channelNo:{}, control:{}", mobileNo, channelNo, control);

        DeviceDO deviceDO = redisCatchStorage.getDevice(mobileNo);
        if (deviceDO == null) {
            return R.fail("jt1078 设备不存在 mobileNo:" + mobileNo);
        }

        T9304 t9304 = new T9304()
                .setChannelNo(channelNo)
                .setControl(control);
        t9304.setClientId(mobileNo);

        try {
            messageManager.notify(mobileNo, t9304).block();
            log.info("[JT1078 云台雨刷控制成功] mobileNo:{}", mobileNo);
            return R.ok();
        } catch (Exception e) {
            log.error("[JT1078 云台雨刷控制失败] mobileNo:{}", mobileNo, e);
            return R.fail("jt1078 云台雨刷控制失败:" + e.getMessage());
        }
    }

    /**
     * 红外补光控制
     */
    @GetMapping("/ptzInfrared/{mobileNo}/{channelNo}")
    public R<Void> ptzInfrared(@PathVariable String mobileNo, @PathVariable int channelNo,
                               @RequestParam int control) {
        log.info("[JT1078 红外补光控制] mobileNo:{}, channelNo:{}, control:{}", mobileNo, channelNo, control);

        DeviceDO deviceDO = redisCatchStorage.getDevice(mobileNo);
        if (deviceDO == null) {
            return R.fail("jt1078 设备不存在 mobileNo:" + mobileNo);
        }

        T9305 t9305 = new T9305()
                .setChannelNo(channelNo)
                .setControl(control);
        t9305.setClientId(mobileNo);

        try {
            messageManager.notify(mobileNo, t9305).block();
            log.info("[JT1078 红外补光控制成功] mobileNo:{}", mobileNo);
            return R.ok();
        } catch (Exception e) {
            log.error("[JT1078 红外补光控制失败] mobileNo:{}", mobileNo, e);
            return R.fail("jt1078 红外补光控制失败:" + e.getMessage());
        }
    }

    /**
     * 云台变倍控制
     */
    @GetMapping("/ptzZoom/{mobileNo}/{channelNo}")
    public R<Void> ptzZoom(@PathVariable String mobileNo, @PathVariable int channelNo,
                           @RequestParam int direction, @RequestParam(defaultValue = "50") int speed) {
        log.info("[JT1078 云台变倍控制] mobileNo:{}, channelNo:{}, direction:{}, speed:{}", mobileNo, channelNo, direction, speed);

        DeviceDO deviceDO = redisCatchStorage.getDevice(mobileNo);
        if (deviceDO == null) {
            return R.fail("jt1078 设备不存在 mobileNo:" + mobileNo);
        }

        T9306 t9306 = new T9306()
                .setChannelNo(channelNo)
                .setDirection(direction)
                .setSpeed(speed);
        t9306.setClientId(mobileNo);

        try {
            messageManager.notify(mobileNo, t9306).block();
            log.info("[JT1078 云台变倍控制成功] mobileNo:{}", mobileNo);
            return R.ok();
        } catch (Exception e) {
            log.error("[JT1078 云台变倍控制失败] mobileNo:{}", mobileNo, e);
            return R.fail("jt1078 云台变倍控制失败:" + e.getMessage());
        }
    }

    /**
     * 查询录像文件列表
     */
    @GetMapping("/queryRecord/{mobileNo}/{channelNo}")
    public R<ArrayList<HashMap<String, Object>>> queryRecord(
            @PathVariable String mobileNo,
            @PathVariable int channelNo,
            @RequestParam @NotBlank(message = "开始时间不能为空") String startTime,
            @RequestParam @NotBlank(message = "结束时间不能为空") String endTime) {
        log.info("[JT1078 查询录像] mobileNo:{}, channelNo:{}, startTime:{}, endTime:{}", mobileNo, channelNo, startTime, endTime);

        DeviceDO deviceDO = redisCatchStorage.getDevice(mobileNo);
        if (deviceDO == null) {
            return R.fail("jt1078 设备不存在 mobileNo:" + mobileNo);
        }

        try {
            String bcdStartTime = convertToBcdTime(startTime);
            String bcdEndTime = convertToBcdTime(endTime);

            T9205 t9205 = new T9205()
                    .setChannelNo(channelNo)
                    .setStartTime(bcdStartTime)
                    .setEndTime(bcdEndTime)
                    .setMediaType(0)
                    .setStreamType(0)
                    .setStorageType(0);
            t9205.setClientId(mobileNo);

            messageManager.notify(mobileNo, t9205).block();

            T1205 recordList = null;
            for (int i = 0; i < 10; i++) {
                Thread.sleep(500);
                recordList = redisCatchStorage.getRecordList(mobileNo);
                if (recordList != null) {
                    break;
                }
            }

            if (recordList == null) {
                return R.fail("获取录像列表超时");
            }

            ArrayList<HashMap<String, Object>> result = new ArrayList<>();
            if (recordList.getItems() != null) {
                for (T1205.Item item : recordList.getItems()) {
                    HashMap<String, Object> record = new HashMap<>();
                    record.put("channel", item.getChannelNo());
                    record.put("startTime", item.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    record.put("endTime", item.getEndTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    record.put("type", item.getMediaType());
                    record.put("fileName", item.getStartTime().format(BCD_FORMATTER) + "_" + item.getEndTime().format(BCD_FORMATTER));
                    record.put("fileSize", item.getSize());
                    record.put("streamType", item.getStreamType());
                    record.put("storageType", item.getStorageType());
                    record.put("warnBit", item.getWarnBit());
                    result.add(record);
                }
            }

            return R.ok(result);
        } catch (Exception e) {
            log.error("[JT1078 查询录像失败] mobileNo:{}", mobileNo, e);
            return R.fail("jt1078 查询录像失败:" + e.getMessage());
        }
    }

    /**
     * 远程录像回放
     */
    @InnerAuth
    @PostMapping("/playback")
    public R<Void> playback(@RequestBody RtpServerParam rtpServer,
                            @RequestParam @NotBlank(message = "开始时间不能为空") String startTime,
                            @RequestParam @NotBlank(message = "结束时间不能为空") String endTime,
                            @RequestParam(defaultValue = "0") int playbackMode,
                            @RequestParam(defaultValue = "0") int playbackSpeed) {
        log.info("[JT1078 录像回放] rtpServer:{}, startTime:{}, endTime:{}", rtpServer, startTime, endTime);

        DeviceDO deviceDO = redisCatchStorage.getDevice(rtpServer.getMobileNo());
        if (deviceDO == null) {
            return R.fail("jt1078 设备不存在 mobileNo:" + rtpServer.getMobileNo());
        }

        try {
            Integer channelNo = rtpServer.getChannel() != null ? rtpServer.getChannel() : 1;
            String bcdStartTime = convertToBcdTime(startTime);
            String bcdEndTime = convertToBcdTime(endTime);

            T9201 t9201 = new T9201()
                    .setIp(rtpServer.getIp())
                    .setTcpPort(rtpServer.getPort())
                    .setUdpPort(rtpServer.getPort())
                    .setChannelNo(channelNo)
                    .setMediaType(0)
                    .setStreamType(0)
                    .setStorageType(0)
                    .setPlaybackMode(playbackMode)
                    .setPlaybackSpeed(playbackSpeed)
                    .setStartTime(bcdStartTime)
                    .setEndTime(bcdEndTime);
            t9201.setClientId(rtpServer.getMobileNo());

            messageManager.notify(rtpServer.getMobileNo(), t9201).block();
            log.info("[JT1078 录像回放成功] mobileNo:{}", rtpServer.getMobileNo());
            return R.ok();
        } catch (Exception e) {
            log.error("[JT1078 录像回放失败] mobileNo:{}", rtpServer.getMobileNo(), e);
            return R.fail("jt1078 录像回放失败:" + e.getMessage());
        }
    }

    /**
     * 录像回放控制
     */
    @InnerAuth
    @GetMapping("/playbackControl/{mobileNo}/{channelNo}")
    public R<Void> playbackControl(@PathVariable String mobileNo, @PathVariable int channelNo,
                                   @RequestParam int playbackMode,
                                   @RequestParam(defaultValue = "0") int playbackSpeed,
                                   @RequestParam(required = false) String playbackTime) {
        log.info("[JT1078 录像回放控制] mobileNo:{}, channelNo:{}, playbackMode:{}", mobileNo, channelNo, playbackMode);

        DeviceDO deviceDO = redisCatchStorage.getDevice(mobileNo);
        if (deviceDO == null) {
            return R.fail("jt1078 设备不存在 mobileNo:" + mobileNo);
        }

        try {
            T9202 t9202 = new T9202()
                    .setChannelNo(channelNo)
                    .setPlaybackMode(playbackMode)
                    .setPlaybackSpeed(playbackSpeed);
            if (playbackTime != null && !playbackTime.isEmpty()) {
                t9202.setPlaybackTime(convertToBcdTime(playbackTime));
            }
            t9202.setClientId(mobileNo);

            messageManager.notify(mobileNo, t9202).block();
            log.info("[JT1078 录像回放控制成功] mobileNo:{}", mobileNo);
            return R.ok();
        } catch (Exception e) {
            log.error("[JT1078 录像回放控制失败] mobileNo:{}", mobileNo, e);
            return R.fail("jt1078 录像回放控制失败:" + e.getMessage());
        }
    }

    private String convertToBcdTime(String timeStr) {
        LocalDateTime time;
        if (timeStr.length() == 19) {
            time = LocalDateTime.parse(timeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } else if (timeStr.length() == 14) {
            time = LocalDateTime.parse(timeStr, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        } else {
            time = LocalDateTime.now();
        }
        return time.format(BCD_FORMATTER);
    }
}
