package com.ruoyi.zlm.hook.event;

import com.ruoyi.zlm.api.domain.ZlmMediaServer;
import org.springframework.context.ApplicationEvent;

/**
 * zlm 心跳事件
 */
public class HookZlmServerKeepaliveEvent extends ApplicationEvent {

    public HookZlmServerKeepaliveEvent(Object source) {
        super(source);
    }

    private ZlmMediaServer mediaServerItem;

    public ZlmMediaServer getMediaServerItem() {
        return mediaServerItem;
    }

    public void setMediaServerItem(ZlmMediaServer mediaServerItem) {
        this.mediaServerItem = mediaServerItem;
    }
}
