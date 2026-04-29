package com.ruoyi.jt1078.server.handle;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.jt1078.protocol.basics.JTMessage;
import com.ruoyi.jt1078.server.model.entity.DeviceDO;
import com.ruoyi.jt1078.server.service.IRedisCatchStorage;
import com.ruoyi.jt1078.server.task.deviceStatus.DeviceStatusTask;
import com.ruoyi.jt1078.server.task.deviceStatus.DeviceStatusTaskRunner;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import io.github.yezhihao.netmc.session.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 心跳处理
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class HeartbeatHandle {

    private int heartBeatInterval = 30;

    private int heartBeatCount = 3;

    private final DeviceStatusTaskRunner deviceStatusTaskRunner;

    private final IRedisCatchStorage redisCatchStorage;

    @Autowired
    private RemoteQsDeviceService remoteQsDeviceService;


    /**
     * 设备心跳处理
     *
     * @param message
     * @param session
     */
    public void handle(JTMessage message, Session session) {
        long expiresTime = heartBeatInterval * heartBeatCount * 1000L;

        if (deviceStatusTaskRunner.containsKey(message.getClientId())) {
            deviceStatusTaskRunner.removeTask(message.getClientId());
            DeviceStatusTask task = DeviceStatusTask.getInstance(message.getClientId(), expiresTime + System.currentTimeMillis(), this::deviceStatusExpire);
            deviceStatusTaskRunner.addTask(task);
        } else {
            DeviceStatusTask task = DeviceStatusTask.getInstance(message.getClientId(), expiresTime + System.currentTimeMillis(), this::deviceStatusExpire);
            deviceStatusTaskRunner.addTask(task);
        }
    }

    public void deviceStatusExpire(String mobileNo) {
        DeviceDO device = redisCatchStorage.getDevice(mobileNo);

        if (device == null) {
            log.warn("[设备不存在] device：{}", mobileNo);
            return;
        }

        log.info("终端设备状态到期！ 手机号：{}， 设备id：{}， 车牌号：{}", device.getMobileNo(), device.getDeviceId(), device.getPlateNo());
        device.setOnline(false);
        redisCatchStorage.updateDevice(device);

        // 同步设备状态到 QS 模块
        try {
            remoteQsDeviceService.updateDeviceStatusByJtMobileNo(device.getMobileNo(), "OFFLINE", SecurityConstants.INNER);
        } catch (Exception e) {
            log.error("[同步设备状态] 设备心跳到期，同步到 QS 模块失败：{}", device.getMobileNo(), e);
        }
    }
}
