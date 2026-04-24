package com.ruoyi.gb28181.transmit.event.request.impl.message;

import com.ruoyi.gb28181.api.domain.Device;
import org.dom4j.Element;

import javax.sip.RequestEvent;

public interface IMessageHandler {
    /**
     * 处理来自设备的信息
     *
     * @param evt
     * @param device
     */
    void handForDevice(RequestEvent evt, Device device, Element element);
}
