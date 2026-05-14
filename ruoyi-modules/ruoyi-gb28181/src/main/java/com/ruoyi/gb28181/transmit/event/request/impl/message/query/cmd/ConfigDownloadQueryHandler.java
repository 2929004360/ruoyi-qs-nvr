package com.ruoyi.gb28181.transmit.event.request.impl.message.query.cmd;

import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.gb28181.api.domain.Gb28181Platform;
import com.ruoyi.gb28181.transmit.event.request.SIPRequestProcessorParent;
import com.ruoyi.gb28181.transmit.event.request.impl.message.IMessageHandler;
import com.ruoyi.gb28181.transmit.event.request.impl.message.query.QueryMessageHandler;
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
 * 处理上级平台的ConfigDownload查询
 */
@Slf4j
@Component
public class ConfigDownloadQueryHandler extends SIPRequestProcessorParent implements InitializingBean, IMessageHandler {

    private final static String cmdType = "ConfigDownload";

    @Autowired
    private QueryMessageHandler queryMessageHandler;

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
        try {
            // 回复200 OK
            responseAck((SIPRequest) evt.getRequest(), Response.OK);
            log.info("[平台级联] 处理ConfigDownload查询完成，平台: {}", platform.getName());
        } catch (SipException | InvalidArgumentException | ParseException e) {
            log.error("[平台级联] 处理ConfigDownload查询失败", e);
        }
    }
}
