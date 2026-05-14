package com.ruoyi.gb28181.transmit.event.response.impl;

import com.ruoyi.gb28181.service.IPlatformSIPCommander;
import com.ruoyi.gb28181.transmit.ISIPProcessorObserver;
import com.ruoyi.gb28181.transmit.event.response.SIPResponseProcessorAbstract;
import gov.nist.javax.sip.message.SIPResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sip.ResponseEvent;
import javax.sip.message.Response;

@Slf4j
@Component
public class RegisterResponseProcessor extends SIPResponseProcessorAbstract {

    private final String method = "REGISTER";

    @Autowired
    private ISIPProcessorObserver sipProcessorObserver;

    @Autowired
    private IPlatformSIPCommander platformSIPCommander;

    @Override
    public void afterPropertiesSet() throws Exception {
        sipProcessorObserver.addResponseProcessor(method, this);
    }

    @Override
    public void process(ResponseEvent evt) {
        SIPResponse response = (SIPResponse) evt.getResponse();
        log.info("[平台级联] 收到REGISTER响应，状态码: {}", response.getStatusCode());

        String localIp;
        try {
            localIp = response.getLocalAddress().getHostAddress();
        } catch (Exception e) {
            log.warn("Failed to get local address from response, using null", e);
            localIp = null;
        }

        platformSIPCommander.handleRegisterResponse(response, localIp);
    }
}
