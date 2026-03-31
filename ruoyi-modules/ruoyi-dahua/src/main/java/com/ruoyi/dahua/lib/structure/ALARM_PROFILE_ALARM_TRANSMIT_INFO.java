package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 报警传输事件(对应结构体ALARM_PROFILE_ALARM_TRANSMIT_INFO)
*/
public class ALARM_PROFILE_ALARM_TRANSMIT_INFO extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 通道号
    */
    public int              nChannelID;
    /**
     * 0:开始 1:停止
    */
    public int              nAction;
    /**
     * 事件发生时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuTime = new NetSDKLib.NET_TIME();
    /**
     * 传感器类型,参见枚举定义 {@link NetSDKLib.NET_SENSE_METHOD}
    */
    public int              emSenseMethod;
    /**
     * 报警用户,用于区分
    */
    public byte[]           szUserID = new byte[128];
    /**
     * 报警源设备的类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_ALARM_TRANSMIT_DEV_SRC_TYPE}
    */
    public int              emDevSrcType;
    /**
     * 报警类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_ALARM_TRANSMIT_ALARM_TYPE}
    */
    public int              emAlarmType;
    /**
     * RealUTC 是否有效，bRealUTC 为 TRUE 时，用 RealUTC，否则用 stuTime 字段
    */
    public int              bRealUTC;
    /**
     * 事件发生的时间（标准UTC）,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX RealUTC = new NetSDKLib.NET_TIME_EX();
    /**
     * 设备编号
    */
    public byte[]           szSN = new byte[32];
    /**
     * 抓拍照片存储地址
    */
    public byte[]           szSnapURL = new byte[128];
    /**
     * 事件公共扩展字段结构体,参见结构体定义 {@link NET_EVENT_INFO_EXTEND}
    */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
    /**
     * 报警附带信息,参见结构体定义 {@link NET_PROFILE_ALARM_INFO}
    */
    public NET_PROFILE_ALARM_INFO stuAlarmInfo = new NET_PROFILE_ALARM_INFO();
    /**
     * 预留字节
    */
    public byte[]           szResvered = new byte[1024];

    public ALARM_PROFILE_ALARM_TRANSMIT_INFO() {
        this.dwSize = this.size();
    }
}

