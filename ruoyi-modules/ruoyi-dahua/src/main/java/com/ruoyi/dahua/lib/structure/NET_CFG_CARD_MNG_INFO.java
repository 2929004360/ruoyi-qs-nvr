package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
单片机兼容cpu卡和ic卡功能切换  对应枚举 NET_EM_CFG_CARD_MNG
*/
public class NET_CFG_CARD_MNG_INFO extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
CPU卡 = 1, IC卡 = 2,
*/
    public			int            nType;
/** 
预留字节
*/
    public			byte[]         szReserved = new byte[1024];

public NET_CFG_CARD_MNG_INFO(){
    this.dwSize=this.size();
}
}

