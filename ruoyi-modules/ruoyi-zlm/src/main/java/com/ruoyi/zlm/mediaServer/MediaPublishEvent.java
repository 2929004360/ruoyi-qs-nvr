package com.ruoyi.zlm.mediaServer;

import com.ruoyi.zlm.api.domain.ZlmMediaServer;
import com.ruoyi.zlm.event.MediaEvent;
import com.ruoyi.zlm.hook.OnPublishHookParam;

/**
 * 推流鉴权事件
 */
public class MediaPublishEvent extends MediaEvent {
    public MediaPublishEvent(Object source) {
        super(source);
    }

    public static MediaPublishEvent getInstance(Object source, OnPublishHookParam hookParam, ZlmMediaServer mediaServer) {
        MediaPublishEvent mediaPublishEvent = new MediaPublishEvent(source);
        mediaPublishEvent.setApp(hookParam.getApp());
        mediaPublishEvent.setStream(hookParam.getStream());
        mediaPublishEvent.setMediaServer(mediaServer);
        mediaPublishEvent.setSchema(hookParam.getSchema());
        mediaPublishEvent.setParams(hookParam.getParams());
        return mediaPublishEvent;
    }

}
