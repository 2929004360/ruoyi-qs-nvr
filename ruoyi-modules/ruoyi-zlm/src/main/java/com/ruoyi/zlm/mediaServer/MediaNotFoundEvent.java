package com.ruoyi.zlm.mediaServer;

import com.ruoyi.zlm.api.domain.ZlmMediaServer;
import com.ruoyi.zlm.api.hook.ABLHookParam;
import com.ruoyi.zlm.event.MediaEvent;
import com.ruoyi.zlm.hook.OnStreamNotFoundHookParam;

/**
 * 流未找到
 */
public class MediaNotFoundEvent extends MediaEvent {
    public MediaNotFoundEvent(Object source) {
        super(source);
    }

    public static MediaNotFoundEvent getInstance(Object source, OnStreamNotFoundHookParam hookParam, ZlmMediaServer mediaServer) {
        MediaNotFoundEvent mediaDepartureEven = new MediaNotFoundEvent(source);
        mediaDepartureEven.setApp(hookParam.getApp());
        mediaDepartureEven.setStream(hookParam.getStream());
        mediaDepartureEven.setSchema(hookParam.getSchema());
        mediaDepartureEven.setMediaServer(mediaServer);
        mediaDepartureEven.setParams(hookParam.getParams());
        return mediaDepartureEven;
    }

    public static MediaNotFoundEvent getInstance(Object source, ABLHookParam hookParam, ZlmMediaServer mediaServer) {
        MediaNotFoundEvent mediaDepartureEven = new MediaNotFoundEvent(source);
        mediaDepartureEven.setApp(hookParam.getApp());
        mediaDepartureEven.setStream(hookParam.getStream());
        mediaDepartureEven.setMediaServer(mediaServer);
        return mediaDepartureEven;
    }
}
