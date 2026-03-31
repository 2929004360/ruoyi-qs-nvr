package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
设备网络信息
*/
public class NET_DEV_NETWORK_INFO extends NetSDKLib.SdkStructure {
/** 
/< 设备IP
*/
    public			byte[]         szDevIP = new byte[64];
/** 
/< 设备端口号
*/
    public			int            nDevPort;
/** 
/< 保留字节
*/
    public			byte[]         szReserved = new byte[516];
}

