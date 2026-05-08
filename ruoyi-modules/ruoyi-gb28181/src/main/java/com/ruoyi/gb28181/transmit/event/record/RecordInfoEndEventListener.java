package com.ruoyi.gb28181.transmit.event.record;

import com.ruoyi.gb28181.api.bean.RecordInfo;
import com.ruoyi.gb28181.service.IDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.SynchronousQueue;

/**
 * 录像查询结束事件监听器
 * 避免把 @EventListener 放在 DeviceServiceImpl 中导致 Spring 代理问题
 */
@Slf4j
@Component
public class RecordInfoEndEventListener {

    @Autowired
    private IDeviceService deviceService;

    @Async("taskExecutor")
    @EventListener
    public void onRecordInfoEndEvent(RecordInfoEndEvent event) {
        if (deviceService instanceof com.ruoyi.gb28181.service.impl.DeviceServiceImpl) {
            Map<String, SynchronousQueue<RecordInfo>> topicSubscribers = 
                ((com.ruoyi.gb28181.service.impl.DeviceServiceImpl) deviceService).getTopicSubscribers();
            
            SynchronousQueue<RecordInfo> queue = topicSubscribers.get("record" + event.getRecordInfo().getSn());
            if (queue != null) {
                queue.offer(event.getRecordInfo());
            }
        }
    }
}
