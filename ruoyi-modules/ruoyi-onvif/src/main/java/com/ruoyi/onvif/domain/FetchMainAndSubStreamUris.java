package com.ruoyi.onvif.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取主副流uri 实体类
 *
 * @Author: 陈江灿
 * @CreateTime: 2025-04-09
 */
@Data
public class FetchMainAndSubStreamUris {

    /**
     * 主键
     */
    private Long id;

    /**
     * 设备ip
     */
    private String ip;

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
    private List<String> streamUris = new ArrayList<>();

    public void addStreamUri(String mediaUri) {
        this.streamUris.add(mediaUri);
    }
}
