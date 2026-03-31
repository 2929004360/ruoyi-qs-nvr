package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * 低功耗设备保活状态回调数据
*/
public class NET_LOW_POWER_KEEPALIVE_CALLBACK_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 是否收到设备保活包
    */
    public int              bKeepAlive;
    /**
     * 用户数据
    */
    public Pointer          dwUserData;
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[1024];

    public NET_LOW_POWER_KEEPALIVE_CALLBACK_INFO() {
    }
}

