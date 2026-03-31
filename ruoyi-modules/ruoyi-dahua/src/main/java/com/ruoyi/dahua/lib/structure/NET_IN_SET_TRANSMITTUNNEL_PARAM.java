package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
CLIENT_SetTransmitTunnelParam 接口输入参数
*/
public class NET_IN_SET_TRANSMITTUNNEL_PARAM extends NetSDKLib.SdkStructure {
/** 
/<  结构体大小
*/
    public			int            dwSize;
/**
*字节对齐
*/
    public  byte[]          szReserved = new byte[4];
/** 
/< 本地服务IP
*/
    public			byte[]         szLoaclIP = new byte[64];
/** 
/< 向上侦听端口的最小值和最大值
*/
    public			int[]          nPort = new int[2];

public NET_IN_SET_TRANSMITTUNNEL_PARAM(){
    this.dwSize=this.size();
}
}

