package com.ruoyi.jt1078.api;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.jt1078.api.domain.Jt1078Device;
import com.ruoyi.jt1078.protocol.t1078.*;
import com.ruoyi.jt1078.server.endpoint.MessageManager;
import com.ruoyi.jt1078.server.model.entity.DeviceDO;
import com.ruoyi.jt1078.server.service.IRedisCatchStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/jt1078")
@RequiredArgsConstructor
public class Jt1078ApiController {

    private final IRedisCatchStorage redisCatchStorage;

    private final MessageManager messageManager;

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
}
