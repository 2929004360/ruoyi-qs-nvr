package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description   获取token入参 
* @date 2022/10/12 11:10:34
*/
public class NET_IN_MAKE_TOKEN extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
客户端唯一标示符
*/
    public			byte[]         szUUID = new byte[128];
/** 
Token保活时间, 范围60~86400
*/
    public			int            nKeepLiveTime;

public			NET_IN_MAKE_TOKEN(){
		this.dwSize=this.size();
}
}

