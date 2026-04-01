package com.ruoyi.zlm.event;


import com.ruoyi.zlm.domain.ZlmMediaServer;
import com.ruoyi.zlm.mediaServer.MediaServerOfflineEvent;
import com.ruoyi.zlm.mediaServer.MediaServerOnlineEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Event事件通知推送器，支持推送在线事件、离线事件
 *
 * @author fengcheng
 */
@Slf4j
@Component
public class EventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void mediaServerOfflineEventPublish(ZlmMediaServer mediaServer){
        MediaServerOfflineEvent outEvent = new MediaServerOfflineEvent(this);
        outEvent.setMediaServer(mediaServer);
        applicationEventPublisher.publishEvent(outEvent);
    }

    public void mediaServerOnlineEventPublish(ZlmMediaServer mediaServer) {
        MediaServerOnlineEvent outEvent = new MediaServerOnlineEvent(this);
        outEvent.setMediaServer(mediaServer);
        applicationEventPublisher.publishEvent(outEvent);
    }

}
