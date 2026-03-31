package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 对应CLIENT_StartSearchDevicesEx接口TTLV回调
*/
public class NET_PG4_NET_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 设备ip
    */
    public byte[]           szIpaddr = new byte[32];
    /**
     * mac地址
    */
    public byte[]           szMask = new byte[32];
    /**
     * 网关
    */
    public byte[]           szGateway = new byte[32];
    /**
     * 1 static; 2 dhcp
    */
    public int              nLanType;
    /**
     * 扩展字段
    */
    public byte[]           szReserved = new byte[2048];

    public NET_PG4_NET_INFO() {
    }
}

