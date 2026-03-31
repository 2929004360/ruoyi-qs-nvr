package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 对应CLIENT_StartSearchDevicesEx接口TTLV回调
*/
public class DEVICE_NET_INFO_TTLV extends NetSDKLib.SdkStructure
{
    public byte[]           szMac = new byte[32];
    /**
     * 参见结构体定义 {@link NET_PG4_DEVICE_INFO}
    */
    public NET_PG4_DEVICE_INFO stBaseInfo = new NET_PG4_DEVICE_INFO();
    /**
     * 扩展字段
    */
    public byte[]           szReserved = new byte[2048];

    public DEVICE_NET_INFO_TTLV() {
    }
}

