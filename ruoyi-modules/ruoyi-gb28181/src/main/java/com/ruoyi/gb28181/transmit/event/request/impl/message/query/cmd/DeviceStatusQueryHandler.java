package com.ruoyi.gb28181.transmit.event.request.impl.message.query.cmd;

import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.gb28181.api.domain.Gb28181Platform;
import com.ruoyi.gb28181.service.IPlatformSIPCommander;
import com.ruoyi.gb28181.transmit.event.request.SIPRequestProcessorParent;
import com.ruoyi.gb28181.transmit.event.request.impl.message.IMessageHandler;
import com.ruoyi.gb28181.transmit.event.request.impl.message.query.QueryMessageHandler;
import com.ruoyi.gb28181.api.utils.XmlUtil;
import gov.nist.javax.sip.message.SIPRequest;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Element;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sip.InvalidArgumentException;
import javax.sip.RequestEvent;
import javax.sip.SipException;
import javax.sip.message.Response;
import java.text.ParseException;

/**
 * 处理上级平台的DeviceStatus查询
 */
@Slf4j
@Component
public class DeviceStatusQueryHandler extends SIPRequestProcessorParent implements InitializingBean, IMessageHandler {

    private final static String cmdType = "DeviceStatus";

    @Autowired
    private QueryMessageHandler queryMessageHandler;

    @Autowired
    private IPlatformSIPCommander platformSIPCommander;

    @Override
    public void afterPropertiesSet() throws Exception {
        queryMessageHandler.addHandler(cmdType, this);
    }

    @Override
    public void handForDevice(RequestEvent evt, Device device, Element rootElement) {
        // 这个方法不处理设备，跳过
    }

    @Override
    public void handForPlatform(RequestEvent evt, Gb28181Platform platform, Element rootElement) {
        log.info("[平台级联] 收到上级平台 DeviceStatus 查询, platform: {}", platform.getName());
        try {
            // 先回复200 OK
            responseAck((SIPRequest) evt.getRequest(), Response.OK);
            // 获取SN
            String snStr = XmlUtil.getText(rootElement, "SN");
            int sn = Integer.parseInt(snStr);
            log.info("[平台级联] 准备发送 DeviceStatus 通知, SN: {}", sn);
            // 发送设备状态
            platformSIPCommander.sendDeviceStatus(platform, sn);
            log.info("[平台级联] DeviceStatus 通知发送完成");
        } catch (SipException | InvalidArgumentException | ParseException | NumberFormatException e) {
            log.error("[平台级联] 处理DeviceStatus查询失败", e);
        }
    }
}
