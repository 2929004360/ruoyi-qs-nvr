package com.ruoyi.gb28181.transmit.event.request.impl.message;

import com.ruoyi.gb28181.api.bean.DeviceNotFoundEvent;
import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.gb28181.api.domain.Gb28181Platform;
import com.ruoyi.gb28181.api.domain.SsrcTransaction;
import com.ruoyi.gb28181.service.IGb28181PlatformService;
import com.ruoyi.gb28181.service.IRedisCatchStorage;
import com.ruoyi.gb28181.session.SipInviteSessionManager;
import com.ruoyi.gb28181.transmit.ISIPProcessorObserver;
import com.ruoyi.gb28181.transmit.event.SipSubscribe;
import com.ruoyi.gb28181.transmit.event.request.ISIPRequestProcessor;
import com.ruoyi.gb28181.transmit.event.request.SIPRequestProcessorParent;
import com.ruoyi.gb28181.transmit.event.sip.SipEvent;
import com.ruoyi.gb28181.api.utils.SipUtils;
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
import javax.sip.header.CSeqHeader;
import javax.sip.header.CallIdHeader;
import javax.sip.message.Response;
import java.text.ParseException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class MessageRequestProcessor extends SIPRequestProcessorParent implements InitializingBean, ISIPRequestProcessor {

    private final String method = "MESSAGE";

    private static final Map<String, IMessageHandler> messageHandlerMap = new ConcurrentHashMap<>();

    @Autowired
    private ISIPProcessorObserver sipProcessorObserver;

    @Autowired
    private SipSubscribe sipSubscribe;

    @Autowired
    private IRedisCatchStorage redisCatchStorage;

    @Autowired
    private SipInviteSessionManager sessionManager;

    @Autowired
    private IGb28181PlatformService gb28181PlatformService;


    @Override
    public void afterPropertiesSet() throws Exception {
        // 添加消息处理的订阅
        sipProcessorObserver.addRequestProcessor(method, this);
    }

    public void addHandler(String name, IMessageHandler handler) {
        messageHandlerMap.put(name, handler);
    }

    @Override
    public void process(RequestEvent evt) {
        SIPRequest sipRequest = (SIPRequest) evt.getRequest();
        String targetId = SipUtils.getUserIdFromToHeader(evt.getRequest());
        CallIdHeader callIdHeader = sipRequest.getCallIdHeader();
        CSeqHeader cSeqHeader = sipRequest.getCSeqHeader();
        // 先从会话内查找
        SsrcTransaction ssrcTransaction = sessionManager.getSsrcTransactionByCallId(callIdHeader.getCallId());
        if (ssrcTransaction != null) {
            targetId = ssrcTransaction.getDeviceId();
        }
        SIPRequest request = (SIPRequest) evt.getRequest();

        try {
            // 先查询是否是设备
            Device device = redisCatchStorage.getDevice(targetId);
            // 如果通过To头找不到设备，尝试通过From头查找（设备响应消息时，To头是平台ID）
            if (device == null) {
                String fromDeviceId = SipUtils.getUserIdFromFromHeader(evt.getRequest());
                log.debug("[Message请求] 通过To头没找到设备，尝试通过From头查找: {}", fromDeviceId);
                device = redisCatchStorage.getDevice(fromDeviceId);
                if (device != null) {
                    targetId = fromDeviceId;
                }
            }
            // 注释掉严格的hostAddress验证，因为在NAT环境或多端口设备上可能不匹配
            // if (device != null) {
            //     String hostAddress = request.getRemoteAddress().getHostAddress();
            //     int remotePort = request.getRemotePort();
            //     if (!device.getHostAddress().equals(hostAddress + ":" + remotePort)) {
            //         device = null;
            //     }
            // }

            // 如果不是设备，查询是否是级联平台
            Gb28181Platform platform = null;
            if (device == null) {
                platform = gb28181PlatformService.selectGb28181PlatformByDeviceGbId(targetId);
            }

            if (device == null && platform == null) {
                // 都不存在则回复404
                responseAck(request, Response.NOT_FOUND, "device or platform " + targetId + " not found");
                log.warn("[未找到设备或平台 ]targetId: {}, callId: {}", targetId, callIdHeader.getCallId());
                SipEvent sipEvent = sipSubscribe.getSubscribe(callIdHeader.getCallId() + cSeqHeader.getSeqNumber());
                if (sipEvent != null && sipEvent.getErrorEvent() != null) {
                    DeviceNotFoundEvent deviceNotFoundEvent = new DeviceNotFoundEvent(callIdHeader.getCallId());
                    SipSubscribe.EventResult eventResult = new SipSubscribe.EventResult(deviceNotFoundEvent);
                    sipEvent.getErrorEvent().response(eventResult);
                }
            } else {
                Element rootElement;
                try {
                    rootElement = getRootElement(evt);
                    if (rootElement == null) {
                        log.error("处理MESSAGE请求  未获取到消息体{}", evt.getRequest());
                        responseAck(request, Response.BAD_REQUEST, "content is null");
                        return;
                    }
                    String name = rootElement.getName();
                    IMessageHandler messageHandler = messageHandlerMap.get(name);
                    if (messageHandler != null) {
                        if (device != null) {
                            messageHandler.handForDevice(evt, device, rootElement);
                        } else if (platform != null) {
                            // 检查平台是否启用了消息通道
                            if (platform.getAsMessageChannel() == null || platform.getAsMessageChannel() != 1) {
                                log.info("[消息通道开关] 平台未启用消息通道，直接回复200，platform: {}", platform.getName());
                                responseAck(request, Response.OK);
                                return;
                            }
                            messageHandler.handForPlatform(evt, platform, rootElement);
                        }
                    } else {
                        // 不支持的message
                        responseAck(request, Response.UNSUPPORTED_MEDIA_TYPE, "Unsupported message type, must Control/Notify/Query/Response");
                    }
                } catch (DocumentException e) {
                    log.warn("解析XML消息内容异常", e);
                    responseAck(request, Response.BAD_REQUEST, e.getMessage());
                }
            }
        } catch (SipException e) {
            log.warn("SIP 回复错误", e);
        } catch (InvalidArgumentException e) {
            log.warn("参数无效", e);
        } catch (ParseException e) {
            log.warn("SIP回复时解析异常", e);
        }
    }
}
