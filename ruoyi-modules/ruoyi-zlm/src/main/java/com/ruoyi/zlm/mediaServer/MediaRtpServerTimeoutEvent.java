package com.ruoyi.zlm.mediaServer;

import com.ruoyi.zlm.api.domain.ZlmMediaServer;
import com.ruoyi.zlm.api.hook.OnStreamChangedHookParam;
import com.ruoyi.zlm.event.MediaEvent;

/**
 * RtpServer收流超时事件
 */
public class MediaRtpServerTimeoutEvent extends MediaEvent {
    public MediaRtpServerTimeoutEvent(Object source) {
        super(source);
    }

    public static MediaRtpServerTimeoutEvent getInstance(Object source, OnStreamChangedHookParam hookParam, ZlmMediaServer mediaServer) {
        MediaRtpServerTimeoutEvent mediaDepartureEven = new MediaRtpServerTimeoutEvent(source);
        mediaDepartureEven.setApp(hookParam.getApp());
        mediaDepartureEven.setStream(hookParam.getStream());
        mediaDepartureEven.setSchema(hookParam.getSchema());
        mediaDepartureEven.setMediaServer(mediaServer);
        return mediaDepartureEven;
    }
}
