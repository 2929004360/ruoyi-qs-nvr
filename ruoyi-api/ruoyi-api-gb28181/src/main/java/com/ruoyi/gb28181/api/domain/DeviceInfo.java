package com.ruoyi.gb28181.api.domain;

import lombok.Data;

import java.util.List;

/**
 * 设备信息对象
 */
@Data
public class DeviceInfo {
    /**
     * 命令类型
     */
    private String cmdType;

    /**
     * 命令序列号
     */
    private String sn;

    /**
     * 目标设备编码
     */
    private String deviceId;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 查询结果标志
     */
    private String result;

    /**
     * 设备生产商
     */
    private String manufacturer;

    /**
     * 设备型号
     */
    private String model;

    /**
     * 设备固件版本
     */
    private String firmware;

    /**
     * 视频输入通道数（标准字段名）
     */
    private Integer channel;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 最大摄像机数（实际返回的字段名）
     */
    private Integer maxCamera;

    /**
     * 最大报警数
     */
    private Integer maxAlarm;

    /**
     * 扩展信息
     */
    private List<String> extraInfo;
}
