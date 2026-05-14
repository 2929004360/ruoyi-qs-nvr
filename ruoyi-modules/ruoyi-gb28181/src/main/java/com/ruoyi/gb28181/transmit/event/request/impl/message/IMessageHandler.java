package com.ruoyi.gb28181.transmit.event.request.impl.message;

import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.gb28181.api.domain.Gb28181Platform;
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

    /**
     * 处理来自上级平台的信息（Query）
     *
     * @param evt
     * @param platform
     */
    default void handForPlatform(RequestEvent evt, Gb28181Platform platform, Element element) {
        // 默认不做任何事，由需要的处理器实现
    }
}
