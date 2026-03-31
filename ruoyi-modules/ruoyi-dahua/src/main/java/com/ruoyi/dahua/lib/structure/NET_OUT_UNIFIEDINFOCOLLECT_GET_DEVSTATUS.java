package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description   获取设备状态出参 
* @date 2022/09/01 15:11:24
*/
public class NET_OUT_UNIFIEDINFOCOLLECT_GET_DEVSTATUS extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
电源电池相关信息
*/
    public			NET_DEVSTATUS_POWER_INFO stuPowerInfo = new NET_DEVSTATUS_POWER_INFO();
/** 
网络相关信息
*/
    public NET_DEVSTATUS_NET_INFO stuNetInfo = new NET_DEVSTATUS_NET_INFO();
/** 
主机软件版本
*/
    public			byte[]         szVersion = new byte[32];
/** 
主机防拆状态 {@link com.ruoyi.dahua.lib.enumeration.NET_EM_TAMPER_STATE}
*/
    public			int            emTamperState;

public NET_OUT_UNIFIEDINFOCOLLECT_GET_DEVSTATUS(){
		this.dwSize=this.size();
}
}

