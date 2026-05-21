package com.ruoyi.gb28181.transmit.event.request.impl.message.notify.cmd;

import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.gb28181.api.domain.Gb28181Platform;
import com.ruoyi.gb28181.transmit.event.request.SIPRequestProcessorParent;
import com.ruoyi.gb28181.transmit.event.request.impl.message.IMessageHandler;
import com.ruoyi.gb28181.transmit.event.request.impl.message.notify.NotifyMessageHandler;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 图像抓拍传输完成通知
 */
@Slf4j
@Component
public class UploadSnapshotFinishedNotifyMessageHandler extends SIPRequestProcessorParent implements InitializingBean, IMessageHandler {

    private final static String cmdType = "UploadSnapshotFinished";

    @Autowired
    private NotifyMessageHandler notifyMessageHandler;

    @Override
    public void afterPropertiesSet() throws Exception {
        notifyMessageHandler.addHandler(cmdType, this);
    }

    @Override
    public void handForDevice(RequestEvent evt, Device device, Element rootElement) {
        log.info("[图像抓拍传输完成通知] 收到设备 {} 的通知", device.getDeviceId());
        try {
            // 解析通知内容
            String deviceId = rootElement.elementTextTrim("DeviceID");
            String sessionId = rootElement.elementTextTrim("SessionID");
            
            List<String> snapshotFileIds = new ArrayList<>();
            Element snapshotListElement = rootElement.element("SnapshotList");
            if (snapshotListElement != null) {
                List<Element> snapshotFileElements = snapshotListElement.elements("SnapshotFileID");
                for (Element snapshotFileElement : snapshotFileElements) {
                    snapshotFileIds.add(snapshotFileElement.getTextTrim());
                }
            }
            
            log.info("[图像抓拍传输完成通知] 设备ID: {}, 会话ID: {}, 抓拍文件ID: {}", 
                    deviceId, sessionId, snapshotFileIds);
            
            // 回复 200 OK
            responseAck((SIPRequest) evt.getRequest(), Response.OK);
            
            // TODO: 可以在这里添加业务逻辑，比如更新抓拍记录状态等
            
        } catch (Exception e) {
            log.error("[图像抓拍传输完成通知] 处理失败: {}", e.getMessage(), e);
            try {
                responseAck((SIPRequest) evt.getRequest(), Response.OK);
            } catch (SipException | InvalidArgumentException | ParseException ex) {
                log.error("[图像抓拍传输完成通知] 回复失败: {}", ex.getMessage());
            }
        }
    }

    @Override
    public void handForPlatform(RequestEvent evt, Gb28181Platform platform, Element rootElement) {
        log.info("[图像抓拍传输完成通知] 收到上级平台 {} 的通知", platform.getName());
        try {
            // 解析通知内容
            String deviceId = rootElement.elementTextTrim("DeviceID");
            String sessionId = rootElement.elementTextTrim("SessionID");
            
            List<String> snapshotFileIds = new ArrayList<>();
            Element snapshotListElement = rootElement.element("SnapshotList");
            if (snapshotListElement != null) {
                List<Element> snapshotFileElements = snapshotListElement.elements("SnapshotFileID");
                for (Element snapshotFileElement : snapshotFileElements) {
                    snapshotFileIds.add(snapshotFileElement.getTextTrim());
                }
            }
            
            log.info("[图像抓拍传输完成通知] 平台ID: {}, 设备ID: {}, 会话ID: {}, 抓拍文件ID: {}", 
                    platform.getServerGbId(), deviceId, sessionId, snapshotFileIds);
            
            // 回复 200 OK
            responseAck((SIPRequest) evt.getRequest(), Response.OK);
            
        } catch (Exception e) {
            log.error("[图像抓拍传输完成通知] 平台通知处理失败: {}", e.getMessage(), e);
            try {
                responseAck((SIPRequest) evt.getRequest(), Response.OK);
            } catch (SipException | InvalidArgumentException | ParseException ex) {
                log.error("[图像抓拍传输完成通知] 平台通知回复失败: {}", ex.getMessage());
            }
        }
    }
}
