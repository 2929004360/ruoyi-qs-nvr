package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  wifi信息 
* @date 2022/08/31 14:44:17
*/
public class NET_WPAN_WIFI_INFO extends NetSDKLib.SdkStructure {
/** 
Wi-Fi参数信息同步使能
*/
    public			int            bSyncEnable;
/** 
wifi名称
*/
    public			byte[]         szSSID = new byte[128];
/** 
wifi密码
*/
    public			byte[]         szPassword = new byte[64];
/** 
预留字段
*/
    public			byte[]         byReserved = new byte[32];

public			NET_WPAN_WIFI_INFO(){
}
}

