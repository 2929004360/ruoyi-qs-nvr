package com.ruoyi.gb28181.transmit.event.request.impl.message.response.cmd;

import com.ruoyi.gb28181.config.SipConfig;
import com.ruoyi.gb28181.domain.Device;
import com.ruoyi.gb28181.domain.DeviceChannel;
import com.ruoyi.gb28181.domain.HandlerCatchData;
import com.ruoyi.gb28181.service.IDeviceChannelService;
import com.ruoyi.gb28181.transmit.event.request.SIPRequestProcessorParent;
import com.ruoyi.gb28181.transmit.event.request.impl.message.IMessageHandler;
import com.ruoyi.gb28181.transmit.event.request.impl.message.response.ResponseMessageHandler;
import com.ruoyi.gb28181.utils.Coordtransform;
import gov.nist.javax.sip.message.SIPRequest;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sip.InvalidArgumentException;
import javax.sip.RequestEvent;
import javax.sip.SipException;
import javax.sip.message.Response;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 目录查询的回复
 */
@Slf4j
@Component
public class CatalogResponseMessageHandler extends SIPRequestProcessorParent implements InitializingBean, IMessageHandler {

    private final String cmdType = "Catalog";

    @Autowired
    private ResponseMessageHandler responseMessageHandler;

    @Autowired
    private SipConfig sipConfig;

    @Autowired
    private IDeviceChannelService deviceChannelService;

    private final ConcurrentLinkedQueue<HandlerCatchData> taskQueue = new ConcurrentLinkedQueue<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        responseMessageHandler.addHandler(cmdType, this);
    }

    @Override
    public void handForDevice(RequestEvent evt, Device device, Element element) {
        taskQueue.offer(new HandlerCatchData(evt, device, element));
        // 回复200 OK
        try {
            responseAck((SIPRequest) evt.getRequest(), Response.OK);
        } catch (SipException | InvalidArgumentException | ParseException e) {
            log.error("[命令发送失败] 目录查询回复: {}", e.getMessage());
        }
    }

    @Scheduled(fixedDelay = 500)
    @Transactional
    public void executeTaskQueue() {
        if (taskQueue.isEmpty()) {
            return;
        }
        List<HandlerCatchData> handlerCatchDataList = new ArrayList<>();
        int size = taskQueue.size();
        for (int i = 0; i < size; i++) {
            HandlerCatchData poll = taskQueue.poll();
            if (poll != null) {
                handlerCatchDataList.add(poll);
            }
        }
        if (handlerCatchDataList.isEmpty()) {
            return;
        }
        for (HandlerCatchData take : handlerCatchDataList) {
            if (take == null) {
                continue;
            }
            RequestEvent evt = take.getEvt();
            int sn = 0;
            // 全局异常捕获，保证下一条可以得到处理
            try {
                Element rootElement = null;
                try {
                    rootElement = getRootElement(take.getEvt(), take.getDevice().getCharset());
                } catch (DocumentException e) {
                    log.error("[xml解析] 失败： ", e);
                    continue;
                }
                if (rootElement == null) {
                    log.warn("[ 收到通道 ] content cannot be null, {}", evt.getRequest());
                    continue;
                }
                Element deviceListElement = rootElement.element("DeviceList");
                Element sumNumElement = rootElement.element("SumNum");
                Element snElement = rootElement.element("SN");

                sn = Integer.parseInt(snElement.getText());
                int sumNum = Integer.parseInt(sumNumElement.getText());

                if (sumNum == 0) {
                    log.info("[收到通道]设备:{}的: 0个", take.getDevice().getDeviceId());
                    // 数据已经完整接收
                    deviceChannelService.cleanChannelsForDevice(take.getDevice().getId());
                    return;
                } else {
                    Iterator<Element> deviceListIterator = deviceListElement.elementIterator();
                    if (deviceListIterator != null) {
                        List<DeviceChannel> channelList = new ArrayList<>();
                        // 遍历DeviceList
                        while (deviceListIterator.hasNext()) {
                            Element itemDevice = deviceListIterator.next();
                            Element channelDeviceElement = itemDevice.element("DeviceID");
                            if (channelDeviceElement == null) {
                                // 总数减一， 避免最后总数不对 无法确定问题
                                continue;
                            }
                            // 从xml解析内容到 DeviceChannel 对象
                            DeviceChannel channel = DeviceChannel.decode(itemDevice);
                            if (channel.getDeviceId() == null) {
                                log.info("[收到目录订阅]：但是解析失败 {}", new String(evt.getRequest().getRawContent()));
                                continue;
                            }
                            channel.setDataDeviceId(take.getDevice().getId());
                            if (channel.getParentId() != null && channel.getParentId().equals(sipConfig.getId())) {
                                channel.setParentId(null);
                            }

                            // 解析通道类型
                            if (channel.getDeviceId().length() <= 8) {
                                // 行政区划
                                Region region = Region.getInstance(channel);
                                regionList.add(region);
                                channel.setChannelType(1);
                            }else if (channel.getDeviceId().length() == 20){
                                // 业务分组/虚拟组织
                                Group group = Group.getInstance(channel);
                                if (group != null) {
                                    channel.setParental(1);
                                    channel.setChannelType(2);
                                    groupList.add(group);
                                }
                                if (channel.getLongitude() != null && channel.getLatitude() != null && channel.getLongitude() > 0 && channel.getLatitude() > 0) {
                                    Double[] wgs84Position = Coordtransform.GCJ02ToWGS84(channel.getLongitude(), channel.getLatitude());
                                    channel.setGbLongitude(wgs84Position[0]);
                                    channel.setGbLatitude(wgs84Position[1]);
                                }
                            }
                            channelList.add(channel);
                        }
                        deviceChannelService.updateChannels(take.getDevice(), channelList);
                        log.info("[收到通道]设备: {} -> {}个，{}/{}", take.getDevice().getDeviceId(), channelList.size(), sn, sumNum);
                    }
                }
            } catch (Exception e) {
                log.warn("[收到通道] 发现未处理的异常, \r\n{}", evt.getRequest());
                log.error("[收到通道] 异常内容： ", e);
            }
        }
    }
}
