package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 对应CLIENT_StartSearchDevicesEx接口TTLV回调
*/
public class NET_PG4_DEVICE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * sn
    */
    public byte[]           szSN = new byte[64];
    /**
     * 设备类型
    */
    public byte[]           szDevClass = new byte[16];
    /**
     * 设备子类型
    */
    public byte[]           szSubClass = new byte[16];
    public byte[]           szType = new byte[64];
    /**
     * 厂商
    */
    public byte[]           szVendor = new byte[32];
    public byte[]           szMachineName = new byte[64];
    /**
     * 0未初始化,1已初始化
    */
    public int              bInit;
    /**
     * 参见结构体定义 {@link NET_PG4_NET_INFO}
    */
    public NET_PG4_NET_INFO stIPv4 = new NET_PG4_NET_INFO();
    /**
     * 参见结构体定义 {@link NET_PG4_SVC_INFO}
    */
    public NET_PG4_SVC_INFO stSvc = new NET_PG4_SVC_INFO();
    /**
     * 扩展字段
    */
    public byte[]           szReserved = new byte[2048];

    public NET_PG4_DEVICE_INFO() {
    }
}

