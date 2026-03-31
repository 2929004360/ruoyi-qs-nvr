package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型 DH_ALARM_INVITE_TIMEOUT (语音呼叫超时事件)对应的数据块描述信息
*/
public class NET_ALARM_INVITE_TIMEOUT_INFO extends NetSDKLib.SdkStructure
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
     * 事件公共扩展字段结构体,参见结构体定义 {@link NET_EVENT_INFO_EXTEND}
    */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
    /**
     * 事件发生的时间,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX stuUTC = new NetSDKLib.NET_TIME_EX();
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1028];

    public NET_ALARM_INVITE_TIMEOUT_INFO() {
    }
}

