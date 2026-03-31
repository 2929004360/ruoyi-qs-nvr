package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/** 
隧道连接断开回调信息
*/
public class NET_TRANSMIT_DISCONNECT_CALLBACK extends NetSDKLib.SdkStructure {
/** 
/< 设备网络信息
*/
    public NET_DEV_NETWORK_INFO stuDevNetInfo = new NET_DEV_NETWORK_INFO();
/** 
/< 用户数据 LDWORD
*/
    public Pointer          dwUserData;
/** 
/< 保留字节
*/
    public			byte[]         szReserved = new byte[1024];
}

