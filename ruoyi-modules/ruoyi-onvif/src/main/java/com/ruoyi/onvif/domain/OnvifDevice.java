package com.ruoyi.onvif.domain;

import lombok.Data;

import java.util.List;


@Data
public class OnvifDevice {

    /**
     * ip
     */
    private String ip;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * host名称
     */
    private String hostName;

    /**
     * 设备厂商
     */
    private String firm;

    /**
     * 设备型号
     */
    private String model;

    /**
     * 固件版本
     */
    private String firmwareVersion;

    /**
     * 球机多条播放
     */
    private List<String> streamUris;
}
