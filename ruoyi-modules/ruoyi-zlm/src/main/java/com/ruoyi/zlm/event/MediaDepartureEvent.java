package com.ruoyi.zlm.event;

import com.ruoyi.zlm.api.domain.ZlmMediaServer;
import com.ruoyi.zlm.api.hook.OnStreamChangedHookParam;

/**
 * 流离开事件
 */
public class MediaDepartureEvent extends MediaEvent {
    public MediaDepartureEvent(Object source) {
        super(source);
    }

    public static MediaDepartureEvent getInstance(Object source, OnStreamChangedHookParam hookParam, ZlmMediaServer mediaServer) {
        MediaDepartureEvent mediaDepartureEven = new MediaDepartureEvent(source);
        mediaDepartureEven.setApp(hookParam.getApp());
        mediaDepartureEven.setStream(hookParam.getStream());
        mediaDepartureEven.setSchema(hookParam.getSchema());
        mediaDepartureEven.setMediaServer(mediaServer);
        return mediaDepartureEven;
    }
}
