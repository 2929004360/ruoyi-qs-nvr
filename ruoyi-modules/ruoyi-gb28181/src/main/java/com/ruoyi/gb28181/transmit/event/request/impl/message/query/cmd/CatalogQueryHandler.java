package com.ruoyi.gb28181.transmit.event.request.impl.message.query.cmd;

import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.gb28181.api.domain.Gb28181Platform;
import com.ruoyi.gb28181.service.IPlatformSIPCommander;
import com.ruoyi.gb28181.task.platform.PlatformCascadeTaskManager;
import com.ruoyi.gb28181.transmit.event.request.SIPRequestProcessorParent;
import com.ruoyi.gb28181.transmit.event.request.impl.message.IMessageHandler;
import com.ruoyi.gb28181.transmit.event.request.impl.message.query.QueryMessageHandler;
import com.ruoyi.gb28181.api.utils.XmlUtil;
import com.ruoyi.qs.api.domain.SimpleDeviceInfo;
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
import java.util.List;

/**
 * 处理上级平台的Catalog查询
 */
@Slf4j
@Component
public class CatalogQueryHandler extends SIPRequestProcessorParent implements InitializingBean, IMessageHandler {

    private final static String cmdType = "Catalog";

    @Autowired
    private QueryMessageHandler queryMessageHandler;

    @Autowired
    private IPlatformSIPCommander platformSIPCommander;

    @Autowired
    private PlatformCascadeTaskManager platformCascadeTaskManager;

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
        log.info("[平台级联] 收到上级平台 Catalog 查询, platform: {}", platform.getName());
        try {
            // 先回复200 OK
            responseAck((SIPRequest) evt.getRequest(), Response.OK);
            // 获取SN
            String snStr = XmlUtil.getText(rootElement, "SN");
            int sn = Integer.parseInt(snStr);
            // 获取设备列表（使用公共方法）
            List<SimpleDeviceInfo> deviceList = platformCascadeTaskManager.getDeviceList(platform);
            log.info("[平台级联] 准备发送 Catalog 通知, SN: {}, 设备数: {}", sn, deviceList.size());
            // 发送目录
            platformSIPCommander.sendCatalog(platform, deviceList, sn);
            log.info("[平台级联] Catalog 通知发送完成");
        } catch (SipException | InvalidArgumentException | ParseException | NumberFormatException e) {
            log.error("[平台级联] 处理Catalog查询失败", e);
        }
    }
}
