package com.ruoyi.gb28181.transmit.event.request.impl.message.response.cmd;

import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.gb28181.api.domain.DeviceInfo;
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
 * DeviceInfo 应答消息处理
 */
@Slf4j
@Component
public class DeviceInfoResponseMessageHandler extends SIPRequestProcessorParent implements InitializingBean, IMessageHandler {

    private final String cmdType = "DeviceInfo";

    @Autowired
    private ResponseMessageHandler responseMessageHandler;

    @Override
    public void afterPropertiesSet() throws Exception {
        responseMessageHandler.addHandler(cmdType, this);
    }

    @Override
    public void handForDevice(RequestEvent evt, Device device, Element rootElement) {
        log.info("接收到DeviceInfo应答消息, 设备: {}", device);
        if (device == null) {
            log.warn("[接收到DeviceInfo应答消息,但是设备不存在]");
            return;
        }
        SIPRequest request = (SIPRequest) evt.getRequest();
        try {
            rootElement = getRootElement(evt, device.getCharset());

            if (rootElement == null) {
                log.warn("[接收到DeviceInfo应答消息] content cannot be null, {}", evt.getRequest());
                try {
                    responseAck((SIPRequest) evt.getRequest(), Response.BAD_REQUEST);
                } catch (SipException | InvalidArgumentException | ParseException e) {
                    log.error("[命令发送失败] DeviceInfo应答消息 BAD_REQUEST: {}", e.getMessage());
                }
                return;
            }

            // 解析 DeviceInfo 对象
            DeviceInfo deviceInfo = new DeviceInfo();
            deviceInfo.setCmdType(XmlUtil.getText(rootElement, "CmdType"));
            deviceInfo.setSn(XmlUtil.getText(rootElement, "SN"));
            deviceInfo.setDeviceId(XmlUtil.getText(rootElement, "DeviceID"));
            deviceInfo.setDeviceName(XmlUtil.getText(rootElement, "DeviceName"));
            deviceInfo.setResult(XmlUtil.getText(rootElement, "Result"));
            deviceInfo.setManufacturer(XmlUtil.getText(rootElement, "Manufacturer"));
            deviceInfo.setModel(XmlUtil.getText(rootElement, "Model"));
            deviceInfo.setFirmware(XmlUtil.getText(rootElement, "Firmware"));
            deviceInfo.setDeviceType(XmlUtil.getText(rootElement, "DeviceType"));
            
            String channelStr = XmlUtil.getText(rootElement, "Channel");
            if (channelStr != null && !channelStr.isEmpty()) {
                deviceInfo.setChannel(Integer.parseInt(channelStr));
            }
            
            String maxCameraStr = XmlUtil.getText(rootElement, "MaxCamera");
            if (maxCameraStr != null && !maxCameraStr.isEmpty()) {
                deviceInfo.setMaxCamera(Integer.parseInt(maxCameraStr));
                // 如果 channel 为空，用 maxCamera 填充
                if (deviceInfo.getChannel() == null) {
                    deviceInfo.setChannel(deviceInfo.getMaxCamera());
                }
            }
            
            String maxAlarmStr = XmlUtil.getText(rootElement, "MaxAlarm");
            if (maxAlarmStr != null && !maxAlarmStr.isEmpty()) {
                deviceInfo.setMaxAlarm(Integer.parseInt(maxAlarmStr));
            }

            // 解析 ExtraInfo
            List<String> extraInfoList = new ArrayList<>();
            List<Element> extraInfoElements = rootElement.elements("ExtraInfo");
            for (Element extraInfoElement : extraInfoElements) {
                extraInfoList.add(extraInfoElement.getText());
            }
            deviceInfo.setExtraInfo(extraInfoList);

            responseMessageHandler.handMessageEvent(rootElement, deviceInfo);

        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        try {
            // 回复200 OK
            responseAck(request, Response.OK);
        } catch (SipException | InvalidArgumentException | ParseException e) {
            log.error("[命令发送失败] DeviceInfo应答消息 200: {}", e.getMessage());
        }

    }
}
