package com.ruoyi.gb28181.transmit;

import com.ruoyi.gb28181.transmit.event.request.ISIPRequestProcessor;
import com.ruoyi.gb28181.transmit.event.response.ISIPResponseProcessor;

import javax.sip.SipListener;

public interface ISIPProcessorObserver extends SipListener {
    public void addRequestProcessor(String method, ISIPRequestProcessor processor);

    public void addResponseProcessor(String method, ISIPResponseProcessor processor);
}
