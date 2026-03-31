package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 事件类型 EVENT_IVS_TEMPERATURE_ALARM(温度报警事件)对应的数据块描述信息
 * @date 2023/01/12 10:19:13
 */
public class NET_DEV_EVENT_TEMPERATURE_ALARM_INFO extends NetSDKLib.SdkStructure {
    /**
     * 通道号
     */
    public int              nChannelID;
    /**
     * 事件类型 0:脉冲,1:开始, 2:停止
     */
    public int              nAction;
    /**
     * 事件发生的时间
     */
    public NET_TIME_EX      stuUTC = new NET_TIME_EX();
    /**
     * 名称
     */
    public byte[]           szName = new byte[128];
    /**
     * 当前温度
     */
    public float            fCurrent;
    /**
     * 正常范围
     */
    public int[]            nRange = new int[2];
    /**
     * GPS信息
     */
    public NetSDKLib.NET_GPS_STATUS_INFO stuGPS = new NetSDKLib.NET_GPS_STATUS_INFO();
    /**
     *  RealUTC 是否有效，bRealUTC 为 TRUE 时，用 stuRealUTC，否则 stuRealUTC 字段无效(用原事件结构体中的 事件发生时间/事件触发时间(UTC) 字段)
     */
    public  int             bRealUTC;
    /**
     * 事件发生的时间(标准UTC时间(不带时区夏令时偏差)), 由于事件的UTC时间在产品线之间使用的差异性, 故增加RealUTC作为标准UTC时间, 平台在收到事件解析首优先级是RealUTC, 其次是UTC.
     */
    public	NET_TIME_EX      stuRealUTC = new NET_TIME_EX();
    /**
     * 预留字节
     */
    public byte[]           szReserved = new byte[984];

    public NET_DEV_EVENT_TEMPERATURE_ALARM_INFO() {
    }
}

