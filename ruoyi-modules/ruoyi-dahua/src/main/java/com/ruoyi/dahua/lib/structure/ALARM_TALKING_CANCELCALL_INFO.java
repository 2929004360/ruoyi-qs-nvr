package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 设备呼叫中取消呼叫事件(对应事件 DH_ALARM_TALKING_CANCELCALL)
*/
public class ALARM_TALKING_CANCELCALL_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 事件发生的时间,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX stuTime = new NetSDKLib.NET_TIME_EX();
    /**
     * 呼叫ID
    */
    public byte[]           szCallID = new byte[32];

    public ALARM_TALKING_CANCELCALL_INFO() {
    }
}

