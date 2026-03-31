package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  接口 CLIENT_SetXRayMultiLevelDetectCFG 的输出参数 
* @date 2022/12/01 17:26:25
*/
public class NET_OUT_SET_XRAY_MULTILEVEL_DETECT_INFO extends NetSDKLib.SdkStructure {
/** 
此结构体大小,必须赋值
*/
    public			int            dwSize;

public NET_OUT_SET_XRAY_MULTILEVEL_DETECT_INFO(){
		this.dwSize=this.size();
}
}

