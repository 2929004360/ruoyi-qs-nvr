package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件触发源信息（APP信息）
*/
public class NET_DHOP_APP_INFO extends NetSDKLib.SdkStructure
{
    /**
     * App名称
    */
    public byte[]           szAppName = new byte[128];
    /**
     * App版本
    */
    public byte[]           szAppVersion = new byte[64];
    /**
     * 预留字节
    */
    public byte[]           byReserved = new byte[1024];

    public NET_DHOP_APP_INFO() {
    }
}

