package com.ruoyi.zlm.api.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class RTPServerParam implements Serializable {

    private Long id;
    private ZlmMediaServer mediaServer;
    private StreamInfo streamInfo;
    private String app;
    private String streamId;
    private String presetSsrc;
    private boolean ssrcCheck;
    private boolean playback;
    private Integer port;
    private boolean onlyAuto;
    private boolean disableAudio;
    private boolean reUsePort;
    private String ssrc;
    private String type;

    /**
     * tcp模式，0时为不启用tcp监听，1时为启用tcp监听，2时为tcp主动连接模式
     */
    private Integer tcpMode;
}
