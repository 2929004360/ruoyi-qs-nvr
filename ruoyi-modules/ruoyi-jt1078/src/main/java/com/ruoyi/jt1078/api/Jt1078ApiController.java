package com.ruoyi.jt1078.api;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.jt1078.api.domain.Jt1078Device;
import com.ruoyi.jt1078.protocol.t1078.T9101;
import com.ruoyi.jt1078.protocol.t1078.T9102;
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
}
