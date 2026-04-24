package com.ruoyi.gb28181.transmit.event.request.impl;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.gb28181.api.domain.SsrcTransaction;
import com.ruoyi.gb28181.session.SipInviteSessionManager;
import com.ruoyi.gb28181.transmit.ISIPProcessorObserver;
import com.ruoyi.gb28181.transmit.SIPSender;
import com.ruoyi.gb28181.transmit.event.request.ISIPRequestProcessor;
import com.ruoyi.gb28181.transmit.event.request.SIPRequestProcessorParent;
import com.ruoyi.zlm.api.RemoteZlmService;
import gov.nist.javax.sip.message.SIPRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sip.RequestEvent;
import javax.sip.SipException;
import javax.sip.header.CallIdHeader;
import javax.sip.message.Response;

@Slf4j
@Component
public class ByeRequestProcessor extends SIPRequestProcessorParent implements InitializingBean, ISIPRequestProcessor {

    public final String method = "BYE";

    @Autowired
    private ISIPProcessorObserver sipProcessorObserver;

    @Autowired
    private SIPSender sipSender;

    @Autowired
    private SipInviteSessionManager sessionManager;

    @Autowired
    private RemoteZlmService remoteZlmService;

    @Override
    public void afterPropertiesSet() throws Exception {
        sipProcessorObserver.addRequestProcessor(method, this);
    }

    @Override
    public void process(RequestEvent evt) {
        SIPRequest request = (SIPRequest) evt.getRequest();
        CallIdHeader callIdHeader = request.getCallIdHeader();
        String callId = callIdHeader.getCallId();

        log.info("[收到 BYE 请求] callId: {}", callId);

        try {
            Response okResponse = getMessageFactory().createResponse(Response.OK, request);
            sipSender.transmitRequest(request.getLocalAddress().getHostAddress(), okResponse);

            SsrcTransaction ssrcTransaction = sessionManager.getSsrcTransactionByCallId(callId);
            if (ssrcTransaction != null) {
                log.info("[BYE 清理资源] deviceId: {}, channelId: {}, app: {}, stream: {}, ssrc: {}", 
                        ssrcTransaction.getDeviceId(), 
                        ssrcTransaction.getChannelId(), 
                        ssrcTransaction.getApp(), 
                        ssrcTransaction.getStream(), 
                        ssrcTransaction.getSsrc());

                sessionManager.removeByCallId(callId);
                remoteZlmService.releaseSsrc(ssrcTransaction.getMediaServerId(), ssrcTransaction.getSsrc(), SecurityConstants.INNER);
                
                RtpServerParam rtpServerParam = new RtpServerParam();
                rtpServerParam.setMediaServerId(ssrcTransaction.getMediaServerId());
                rtpServerParam.setApp(ssrcTransaction.getApp());
                rtpServerParam.setStream(ssrcTransaction.getStream());
                rtpServerParam.setSsrc(ssrcTransaction.getSsrc());
                rtpServerParam.setGbDeviceId(ssrcTransaction.getDeviceId());
                rtpServerParam.setGbChannelId(ssrcTransaction.getChannelId());
                remoteZlmService.closeRTPServer(ssrcTransaction.getMediaServerId(), rtpServerParam, SecurityConstants.INNER);
            }
        } catch (Exception e) {
            log.error("[BYE 处理异常]", e);
        }
    }
}
