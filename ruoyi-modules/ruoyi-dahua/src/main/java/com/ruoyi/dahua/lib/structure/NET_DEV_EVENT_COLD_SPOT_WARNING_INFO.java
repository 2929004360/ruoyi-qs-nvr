package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型 EVENT_IVS_COLD_SPOT_WARNING (冷点报警)对应的数据块描述信息
*/
public class NET_DEV_EVENT_COLD_SPOT_WARNING_INFO extends NetSDKLib.SdkStructure
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
     * 冷点位置 归一化到[0,8191]上的坐标点,参见结构体定义 {@link NET_POINT_EX}
    */
    public NET_POINT_EX     stuPoint = new NET_POINT_EX();
    /**
     * 冷点温度值
    */
    public float            fColdSpotValue;
    /**
     * 当前配置的温度单位,参见枚举定义 {@link NetSDKLib.NET_TEMPERATURE_UNIT}
    */
    public int              emTemperatureUnit;
    /**
     * 冷点报警阈值
    */
    public float            fThreshold;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1020];

    public NET_DEV_EVENT_COLD_SPOT_WARNING_INFO() {
    }
}

