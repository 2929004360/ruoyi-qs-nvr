package com.ruoyi.zlm.api.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * ONVIF回放拉流播放
 *
 * @FileName OnvifPlayback
 * @Description
 * @Author fengcheng
 * @date 2026-04-02
 **/
@Data
public class OnvifPlayback implements Serializable {

    /**
     * 设备id
     */
    private Long deviceId;

    /**
     * 设备IP
     */
    private String deviceIp;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 录制令牌
     */
    private String recordingToken;

    /**
     * 轨道令牌
     */
    private String trackToken;

    /**
     * 应用名称
     */
    private String app;

    /**
     * 流名称
     */
    private String stream;

    /**
     * 转协议时是否开启音频
     */
    private boolean enable_audio;

    /**
     * rtsp 拉流时，拉流方式，0：tcp，1：udp，2：组播
     */
    private String rtp_type;

    /**
     * 超时时间
     */
    private int timeOut;
}
