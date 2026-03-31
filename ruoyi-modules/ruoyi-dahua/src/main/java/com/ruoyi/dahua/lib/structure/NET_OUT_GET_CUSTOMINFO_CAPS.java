package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_GetCustomInfoCaps 输出参数 
* @date 2022/05/11 20:24:43
*/
public class NET_OUT_GET_CUSTOMINFO_CAPS extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
为true时，订阅参数是XRaySubscribeRule，类型是int
格式如XRaySubscribeRule=1，取值范围：0表示取UploadPlatform配置，1表示取DangerPackageCondition配置
*/
    public			int            bSupportXRaySubscribeRule;

public NET_OUT_GET_CUSTOMINFO_CAPS(){
		this.dwSize=this.size();
}
}

