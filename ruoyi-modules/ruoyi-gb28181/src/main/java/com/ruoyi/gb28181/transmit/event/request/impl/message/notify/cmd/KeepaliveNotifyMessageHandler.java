package com.ruoyi.gb28181.transmit.event.request.impl.message.notify.cmd;

import com.ruoyi.gb28181.api.common.RemoteAddressInfo;
import com.ruoyi.gb28181.config.UserSetting;
import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.gb28181.api.domain.Gb28181Platform;
import com.ruoyi.gb28181.api.domain.SipMsgInfo;
import com.ruoyi.gb28181.service.IDeviceService;

import com.ruoyi.gb28181.task.deviceStatus.DeviceStatusTaskRunner;
import com.ruoyi.gb28181.transmit.event.request.SIPRequestProcessorParent;
import com.ruoyi.gb28181.transmit.event.request.impl.message.IMessageHandler;
import com.ruoyi.gb28181.transmit.event.request.impl.message.notify.NotifyMessageHandler;
import com.ruoyi.gb28181.api.utils.DateUtil;
import com.ruoyi.gb28181.utils.IpPortUtil;
import com.ruoyi.gb28181.api.utils.SipUtils;
import gov.nist.javax.sip.message.SIPRequest;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Element;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sip.InvalidArgumentException;
import javax.sip.RequestEvent;
import javax.sip.SipException;
import javax.sip.message.Response;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 状态信息(心跳)报送
 */
@Slf4j
@Component
public class KeepaliveNotifyMessageHandler extends SIPRequestProcessorParent implements InitializingBean, IMessageHandler {

    private final static String cmdType = "Keepalive";

    private final ConcurrentLinkedQueue<SipMsgInfo> taskQueue = new ConcurrentLinkedQueue<>();

    @Autowired
    private NotifyMessageHandler notifyMessageHandler;

    @Autowired
    private IDeviceService deviceService;

    @Autowired
    private DeviceStatusTaskRunner statusTaskRunner;

    @Autowired
    private UserSetting userSetting;

    @Override
    public void afterPropertiesSet() throws Exception {
        notifyMessageHandler.addHandler(cmdType, this);
    }

    @Override
    public void handForDevice(RequestEvent evt, Device device, Element rootElement) {
        if (taskQueue.size() >= userSetting.getMaxNotifyCountQueue()) {
            log.error("[心跳] 待处理消息队列已满 {}，返回486 BUSY_HERE，消息不做处理", userSetting.getMaxNotifyCountQueue());
            return;
        }
        taskQueue.offer(new SipMsgInfo(evt, device, rootElement));
    }

    @Override
    public void handForPlatform(RequestEvent evt, Gb28181Platform platform, Element rootElement) {
        log.info("[平台心跳] 收到上级平台 {} 的心跳消息", platform.getName());
        // 上级平台心跳直接回复200 OK，不需要排队处理
        try {
            responseAck((SIPRequest) evt.getRequest(), Response.OK);
            log.info("[平台心跳] 回复上级平台心跳成功");
        } catch (SipException | InvalidArgumentException | ParseException e) {
            log.error("[命令发送失败] 平台心跳回复: {}", e.getMessage());
        }
    }

    @Scheduled(fixedDelay = 100)
    public void executeTaskQueue() {
        if (taskQueue.isEmpty()) {
            return;
        }
        List<SipMsgInfo> handlerCatchDataList = new ArrayList<>();
        int size = taskQueue.size();
        for (int i = 0; i < size; i++) {
            SipMsgInfo poll = taskQueue.poll();
            if (poll != null) {
                handlerCatchDataList.add(poll);
            }
        }
        if (handlerCatchDataList.isEmpty()) {
            return;
        }
        List<Device> deviceListForUpdate = new ArrayList<>();
        for (SipMsgInfo sipMsgInfo : handlerCatchDataList) {
            if (sipMsgInfo == null) {
                continue;
            }
            RequestEvent evt = sipMsgInfo.getEvt();
            // 回复200 OK
            try {
                responseAck((SIPRequest) evt.getRequest(), Response.OK);
            } catch (SipException | InvalidArgumentException | ParseException e) {
                log.error("[命令发送失败] 心跳回复: {}", e.getMessage());
            }
            Device device = sipMsgInfo.getDevice();
            SIPRequest request = (SIPRequest) evt.getRequest();

            RemoteAddressInfo remoteAddressInfo = SipUtils.getRemoteAddressFromRequest(request, userSetting.getSipUseSourceIpAsRemoteAddress());
            if (device.getIp() == null || !device.getIp().equalsIgnoreCase(remoteAddressInfo.getIp()) || device.getPort() != remoteAddressInfo.getPort()) {
                log.info("[收到心跳] 地址变化, {}({}), {}:{}->{}", device.getName(), device.getDeviceId(), remoteAddressInfo.getIp(), remoteAddressInfo.getPort(), request.getLocalAddress().getHostAddress());
                device.setPort(remoteAddressInfo.getPort());
                device.setHostAddress(IpPortUtil.concatenateIpAndPort(remoteAddressInfo.getIp(), String.valueOf(remoteAddressInfo.getPort())));
                device.setIp(remoteAddressInfo.getIp());
                device.setLocalIp(request.getLocalAddress().getHostAddress());
            }

            device.setKeepaliveTime(DateUtil.getNow());

            if (device.isOnLine()) {
                deviceListForUpdate.add(device);
                long expiresTime = Math.min(device.getExpires(), device.getHeartBeatInterval() * device.getHeartBeatCount()) * 1000L;
                if (statusTaskRunner.containsKey(device.getDeviceId())) {
                    statusTaskRunner.updateDelay(device.getDeviceId(), expiresTime + System.currentTimeMillis());
                }
            } else {
                if (userSetting.getGbDeviceOnline() == 1) {
                    // 对于已经离线的设备判断他的注册是否已经过期
                    deviceService.online(device, null);
                }
            }
        }
        if (!deviceListForUpdate.isEmpty()) {
            deviceService.updateDeviceList(deviceListForUpdate);
        }
    }
}
