package com.ruoyi.gb28181.transmit.event.request.impl.message;

import com.ruoyi.gb28181.common.ErrorCode;
import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.gb28181.transmit.event.MessageSubscribe;
import com.ruoyi.gb28181.transmit.event.request.SIPRequestProcessorParent;
import com.ruoyi.gb28181.transmit.event.sip.MessageEvent;
import com.ruoyi.gb28181.api.utils.XmlUtil;
import gov.nist.javax.sip.message.SIPRequest;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sip.InvalidArgumentException;
import javax.sip.RequestEvent;
import javax.sip.SipException;
import javax.sip.message.Response;
import java.text.ParseException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public abstract class MessageHandlerAbstract extends SIPRequestProcessorParent implements IMessageHandler {

    @Autowired
    private MessageSubscribe messageSubscribe;

    public Map<String, IMessageHandler> messageHandlerMap = new ConcurrentHashMap<>();

    public void addHandler(String cmdType, IMessageHandler messageHandler) {
        messageHandlerMap.put(cmdType, messageHandler);
    }

    @Override
    public void handForDevice(RequestEvent evt, Device device, Element element) {
        String cmd = XmlUtil.getText(element, "CmdType");
        if (cmd == null) {
            try {
                responseAck((SIPRequest) evt.getRequest(), Response.OK);
            } catch (SipException | InvalidArgumentException | ParseException e) {
                log.error("[命令发送失败] 回复200 OK: {}", e.getMessage());
            }
            return;
        }
        IMessageHandler messageHandler = messageHandlerMap.get(cmd);

        if (messageHandler != null) {
            messageHandler.handForDevice(evt, device, element);
        }else {
            handMessageEvent(element, null);
        }
    }

    public void handMessageEvent(Element element, Object data) {
        String cmd = XmlUtil.getText(element, "CmdType");
        String sn = XmlUtil.getText(element, "SN");
        MessageEvent<Object> subscribe = (MessageEvent<Object>)messageSubscribe.getSubscribe(cmd + sn);
        if (subscribe != null && subscribe.getCallback() != null) {
            String result = XmlUtil.getText(element, "Result");
            if (result == null || "OK".equalsIgnoreCase(result) || data != null) {
                subscribe.getCallback().run(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMsg(), data);
            }else {
                subscribe.getCallback().run(ErrorCode.ERROR100.getCode(), ErrorCode.ERROR100.getMsg(), result);
            }
            messageSubscribe.removeSubscribe(cmd + sn);
        }
    }
}
