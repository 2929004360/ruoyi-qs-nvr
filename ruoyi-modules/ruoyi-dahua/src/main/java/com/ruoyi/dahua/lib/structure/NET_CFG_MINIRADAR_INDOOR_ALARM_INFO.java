package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description Mini雷达报警配置, 对应配置项 NET_EM_CFG_MINIRADAR_INDOOR_ALARM
 * @date 2022/08/04 10:13:30
 */
public class NET_CFG_MINIRADAR_INDOOR_ALARM_INFO extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 呼吸异常报警配置
     */
    public NET_CFG_MINIRADAR_BREATHE stuBreathe = new NET_CFG_MINIRADAR_BREATHE();
    /**
     * 心率呼吸异常报警
     */
    public NET_CFG_MINIRADAR_HEATERATE stuHeateRate = new NET_CFG_MINIRADAR_HEATERATE();
    /**
     * 在/离床报警
     */
    public NET_CFG_MINIRADAR_AFBSTATUS stuAFBStatus = new NET_CFG_MINIRADAR_AFBSTATUS();
    /**
     * 跌倒异常报警
     */
    public NET_CFG_MINIRADAR_FALLALARM stuFallAlarm = new NET_CFG_MINIRADAR_FALLALARM();
    /**
     * 人数异常报警
     */
    public NET_CFG_MINIRADAR_NUMALARM stuNumAlarm = new NET_CFG_MINIRADAR_NUMALARM();
    /**
     * 驻留报警
     */
    public NET_CFG_MINIRADAR_RESIDENTALARM stuResidentAlarm = new NET_CFG_MINIRADAR_RESIDENTALARM();

    public NET_CFG_MINIRADAR_INDOOR_ALARM_INFO() {
        this.dwSize = this.size();
    }
}

