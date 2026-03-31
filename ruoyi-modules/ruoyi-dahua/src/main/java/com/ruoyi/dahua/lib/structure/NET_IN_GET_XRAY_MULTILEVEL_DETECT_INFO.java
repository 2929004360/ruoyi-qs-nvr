package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  接口 CLIENT_GetXRayMultiLevelDetectCFG 的输入参数 
* @date 2022/12/01 16:20:33
*/
public class NET_IN_GET_XRAY_MULTILEVEL_DETECT_INFO extends NetSDKLib.SdkStructure {
/** 
此结构体大小,必须赋值
*/
    public			int            dwSize;

public NET_IN_GET_XRAY_MULTILEVEL_DETECT_INFO(){
		this.dwSize=this.size();
}
}

