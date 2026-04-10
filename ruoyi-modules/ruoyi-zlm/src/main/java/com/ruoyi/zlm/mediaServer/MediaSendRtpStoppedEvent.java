package com.ruoyi.zlm.mediaServer;

import com.ruoyi.zlm.api.domain.ZlmMediaServer;
import com.ruoyi.zlm.hook.OnStreamNotFoundHookParam;
import org.springframework.context.ApplicationEvent;

/**
 * 发送流停止事件
 */
public class MediaSendRtpStoppedEvent extends ApplicationEvent {
    public MediaSendRtpStoppedEvent(Object source) {
        super(source);
    }

    private String app;

    private String stream;

    private ZlmMediaServer mediaServer;

    public static MediaSendRtpStoppedEvent getInstance(Object source, OnStreamNotFoundHookParam hookParam, ZlmMediaServer mediaServer) {
        MediaSendRtpStoppedEvent mediaDepartureEven = new MediaSendRtpStoppedEvent(source);
        mediaDepartureEven.setApp(hookParam.getApp());
        mediaDepartureEven.setStream(hookParam.getStream());
        mediaDepartureEven.setMediaServer(mediaServer);
        return mediaDepartureEven;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public ZlmMediaServer getMediaServer() {
        return mediaServer;
    }

    public void setMediaServer(ZlmMediaServer mediaServer) {
        this.mediaServer = mediaServer;
    }
}
