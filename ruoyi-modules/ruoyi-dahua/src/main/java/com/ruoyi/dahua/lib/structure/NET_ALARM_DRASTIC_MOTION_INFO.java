package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型 DH_ALARM_DRASTIC_MOTION (剧烈运动报警)对应的数据块描述信息
*/
public class NET_ALARM_DRASTIC_MOTION_INFO extends NetSDKLib.SdkStructure
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
     * 用户ID
    */
    public byte[]           szUserID = new byte[32];
    /**
     * 用户名
    */
    public byte[]           szUserName = new byte[128];
    /**
     * GPS信息,参见结构体定义 {@link NetSDKLib.NET_GPS_STATUS_INFO}
    */
    public NetSDKLib.NET_GPS_STATUS_INFO stuGPSStatusInfo = new NetSDKLib.NET_GPS_STATUS_INFO();
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1020];

    public NET_ALARM_DRASTIC_MOTION_INFO() {
    }
}

