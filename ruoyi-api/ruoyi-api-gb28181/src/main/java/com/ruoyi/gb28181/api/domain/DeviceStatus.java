package com.ruoyi.gb28181.api.domain;

import lombok.Data;

import java.util.List;

/**
 * 设备状态对象
 */
@Data
public class DeviceStatus {
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
     * 查询结果标志
     */
    private String result;

    /**
     * 是否在线 (ONLINE/OFFLINE)
     */
    private String online;

    /**
     * 是否正常工作
     */
    private String status;

    /**
     * 不正常工作原因
     */
    private String reason;

    /**
     * 是否编码
     */
    private String encode;

    /**
     * 是否录像
     */
    private String record;

    /**
     * 设备时间和日期
     */
    private String deviceTime;

    /**
     * 报警设备状态列表
     */
    private List<AlarmStatusItem> alarmStatus;

    /**
     * 扩展信息
     */
    private List<String> extraInfo;

    @Data
    public static class AlarmStatusItem {
        /**
         * 报警设备编码
         */
        private String deviceId;

        /**
         * 报警设备状态 (ONDUTY/OFFDUTY/ALARM)
         */
        private String dutyStatus;
    }
}
