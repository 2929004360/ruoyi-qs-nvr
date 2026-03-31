package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型 DH_ALARM_BATTERY_ABNORMAL_STATE_ALARM (大电池拆除报警)对应的数据块描述信息
*/
public class NET_ALARM_BATTERY_ABNORMAL_STATE_ALARM_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 通道号
    */
    public int              nChannelID;
    /**
     * 0:脉冲,1:开始, 2:停止
    */
    public int              nAction;
    /**
     * 扩展协议字段,参见结构体定义 {@link NET_EVENT_INFO_EXTEND}
    */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
    /**
     * 事件发生的时间,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX stuUTC = new NetSDKLib.NET_TIME_EX();
    /**
     * 监测项类型："State"：状态
    */
    public byte[]           szName = new byte[128];
    /**
     * GPS信息,参见结构体定义 {@link NetSDKLib.NET_GPS_STATUS_INFO}
    */
    public NetSDKLib.NET_GPS_STATUS_INFO stuGPSStatusInfo = new NetSDKLib.NET_GPS_STATUS_INFO();
    /**
     * 返回正常状态范围： [0,1]
    */
    public int[]            nRange = new int[2];
    /**
     * 返回当前异常状态值(-1 电池不存在，0 充电中，1 充电完成，其它 异常)
    */
    public int              nCurrent;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1024];

    public NET_ALARM_BATTERY_ABNORMAL_STATE_ALARM_INFO() {
    }
}

