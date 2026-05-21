package com.ruoyi.gb28181.transmit.event.request.impl.message.response.cmd;

import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.gb28181.api.domain.DeviceStatus;
import com.ruoyi.gb28181.api.utils.XmlUtil;
import com.ruoyi.gb28181.transmit.event.request.SIPRequestProcessorParent;
import com.ruoyi.gb28181.transmit.event.request.impl.message.IMessageHandler;
import com.ruoyi.gb28181.transmit.event.request.impl.message.response.ResponseMessageHandler;
import gov.nist.javax.sip.message.SIPRequest;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sip.InvalidArgumentException;
import javax.sip.RequestEvent;
import javax.sip.SipException;
import javax.sip.message.Response;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * DeviceStatus 应答消息处理
 */
@Slf4j
@Component
public class DeviceStatusResponseMessageHandler extends SIPRequestProcessorParent implements InitializingBean, IMessageHandler {

    private final String cmdType = "DeviceStatus";

    @Autowired
    private ResponseMessageHandler responseMessageHandler;

    @Override
    public void afterPropertiesSet() throws Exception {
        responseMessageHandler.addHandler(cmdType, this);
    }

    @Override
    public void handForDevice(RequestEvent evt, Device device, Element rootElement) {
        log.info("接收到DeviceStatus应答消息, 设备: {}", device);
        if (device == null) {
            log.warn("[接收到DeviceStatus应答消息,但是设备不存在]");
            return;
        }
        SIPRequest request = (SIPRequest) evt.getRequest();
        try {
            rootElement = getRootElement(evt, device.getCharset());

            if (rootElement == null) {
                log.warn("[接收到DeviceStatus应答消息] content cannot be null, {}", evt.getRequest());
                try {
                    responseAck((SIPRequest) evt.getRequest(), Response.BAD_REQUEST);
                } catch (SipException | InvalidArgumentException | ParseException e) {
                    log.error("[命令发送失败] DeviceStatus应答消息 BAD_REQUEST: {}", e.getMessage());
                }
                return;
            }

            // 解析 DeviceStatus 对象
            DeviceStatus deviceStatus = new DeviceStatus();
            deviceStatus.setCmdType(XmlUtil.getText(rootElement, "CmdType"));
            deviceStatus.setSn(XmlUtil.getText(rootElement, "SN"));
            deviceStatus.setDeviceId(XmlUtil.getText(rootElement, "DeviceID"));
            deviceStatus.setResult(XmlUtil.getText(rootElement, "Result"));
            deviceStatus.setOnline(XmlUtil.getText(rootElement, "Online"));
            deviceStatus.setStatus(XmlUtil.getText(rootElement, "Status"));
            deviceStatus.setReason(XmlUtil.getText(rootElement, "Reason"));
            deviceStatus.setEncode(XmlUtil.getText(rootElement, "Encode"));
            deviceStatus.setRecord(XmlUtil.getText(rootElement, "Record"));
            deviceStatus.setDeviceTime(XmlUtil.getText(rootElement, "DeviceTime"));

            // 解析 AlarmStatus
            Element alarmStatusElement = rootElement.element("AlarmStatus");
            if (alarmStatusElement != null) {
                List<DeviceStatus.AlarmStatusItem> alarmStatusList = new ArrayList<>();
                List<Element> itemElements = alarmStatusElement.elements("Item");
                for (Element itemElement : itemElements) {
                    DeviceStatus.AlarmStatusItem item = new DeviceStatus.AlarmStatusItem();
                    item.setDeviceId(XmlUtil.getText(itemElement, "DeviceID"));
                    item.setDutyStatus(XmlUtil.getText(itemElement, "DutyStatus"));
                    alarmStatusList.add(item);
                }
                deviceStatus.setAlarmStatus(alarmStatusList);
            }

            // 解析 ExtraInfo
            List<String> extraInfoList = new ArrayList<>();
            List<Element> extraInfoElements = rootElement.elements("ExtraInfo");
            for (Element extraInfoElement : extraInfoElements) {
                extraInfoList.add(extraInfoElement.getText());
            }
            deviceStatus.setExtraInfo(extraInfoList);

            responseMessageHandler.handMessageEvent(rootElement, deviceStatus);

        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        try {
            // 回复200 OK
            responseAck(request, Response.OK);
        } catch (SipException | InvalidArgumentException | ParseException e) {
            log.error("[命令发送失败] DeviceStatus应答消息 200: {}", e.getMessage());
        }

    }
}
