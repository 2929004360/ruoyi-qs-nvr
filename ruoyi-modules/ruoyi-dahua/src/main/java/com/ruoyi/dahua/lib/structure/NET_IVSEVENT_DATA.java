package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * 报警事件详情
*/
public class NET_IVSEVENT_DATA extends NetSDKLib.SdkStructure
{
    /**
     * 事件类型,参见智能事件类型，如 EVENT_IVS_ALL
    */
    public int              dwAlarmType;
    /**
     * 智能事件枚举对应的结构体大小
    */
    public int              datalen;
    /**
     * 智能事件枚举对应的结构体指针
    */
    public Pointer          data;
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[64];

    public NET_IVSEVENT_DATA() {
    }
}

