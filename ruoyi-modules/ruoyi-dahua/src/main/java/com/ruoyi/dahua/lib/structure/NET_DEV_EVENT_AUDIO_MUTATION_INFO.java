package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型 EVENT_IVS_AUDIO_MUTATION (声强突变事件)对应的数据块描述信息
*/
public class NET_DEV_EVENT_AUDIO_MUTATION_INFO extends NetSDKLib.SdkStructure
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
     * 事件别名
    */
    public byte[]           szName = new byte[32];
    /**
     * 开始时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuStartTime = new NetSDKLib.NET_TIME();
    /**
     * 事件的确认ID
    */
    public int              nACK;
    /**
     * GroupID事件组ID
    */
    public int              nGroupID;
    /**
     * 相对事件时间戳,单位毫秒
    */
    public double           dbPTS;
    /**
     * 事件发生时间,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX stuUTC = new NetSDKLib.NET_TIME_EX();
    /**
     * 事件编号
    */
    public int              nEventID;
    /**
     * 声音类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_AUDIO_MUTATION_ALARM_TYPE}
    */
    public int              emAudioType;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1020];

    public NET_DEV_EVENT_AUDIO_MUTATION_INFO() {
    }
}

