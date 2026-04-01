package com.ruoyi.zlm.hook.event;

import com.ruoyi.zlm.domain.ZlmMediaServer;
import org.springframework.context.ApplicationEvent;

/**
 * zlm server_start事件
 */
public class HookZlmServerStartEvent extends ApplicationEvent {

    public HookZlmServerStartEvent(Object source) {
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
