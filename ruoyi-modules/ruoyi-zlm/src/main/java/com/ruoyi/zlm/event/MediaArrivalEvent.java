package com.ruoyi.zlm.event;

import com.ruoyi.zlm.api.domain.MediaInfo;
import com.ruoyi.zlm.api.domain.StreamContent;
import com.ruoyi.zlm.api.domain.ZlmMediaServer;
import com.ruoyi.zlm.api.hook.OnStreamArriveABLHookParam;
import com.ruoyi.zlm.api.hook.OnStreamChangedHookParam;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 流到来事件
 */

public class MediaArrivalEvent extends MediaEvent {
    public MediaArrivalEvent(Object source) {
        super(source);
    }

    public static MediaArrivalEvent getInstance(Object source, OnStreamChangedHookParam hookParam, ZlmMediaServer mediaServer, String serverId) {
        MediaArrivalEvent mediaArrivalEvent = new MediaArrivalEvent(source);
        mediaArrivalEvent.setMediaInfo(MediaInfo.getInstance(hookParam, mediaServer, serverId));
        mediaArrivalEvent.setApp(hookParam.getApp());
        mediaArrivalEvent.setStream(hookParam.getStream());
        mediaArrivalEvent.setMediaServer(mediaServer);
        mediaArrivalEvent.setSchema(hookParam.getSchema());
        mediaArrivalEvent.setSchema(hookParam.getSchema());
        mediaArrivalEvent.setParamMap(hookParam.getParamMap());
        return mediaArrivalEvent;
    }

    public static MediaArrivalEvent getInstance(Object source, OnStreamArriveABLHookParam hookParam, ZlmMediaServer mediaServer) {
        MediaArrivalEvent mediaArrivalEvent = new MediaArrivalEvent(source);
        mediaArrivalEvent.setMediaInfo(MediaInfo.getInstance(hookParam, mediaServer));
        mediaArrivalEvent.setApp(hookParam.getApp());
        mediaArrivalEvent.setStream(hookParam.getStream());
        mediaArrivalEvent.setMediaServer(mediaServer);
        mediaArrivalEvent.setCallId(hookParam.getCallId());
        return mediaArrivalEvent;
    }

    @Getter
    @Setter
    private MediaInfo mediaInfo;

    @Getter
    @Setter
    private String callId;

    @Getter
    @Setter
    private StreamContent streamInfo;

    @Getter
    @Setter
    private Map<String, String> paramMap;

    @Getter
    @Setter
    private String serverId;


}
