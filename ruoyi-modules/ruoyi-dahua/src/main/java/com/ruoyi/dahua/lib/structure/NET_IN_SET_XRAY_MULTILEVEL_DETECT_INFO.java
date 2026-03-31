package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  接口 CLIENT_SetXRayMultiLevelDetectCFG 的输入参数 
* @date 2022/12/01 17:25:15
*/
public class NET_IN_SET_XRAY_MULTILEVEL_DETECT_INFO extends NetSDKLib.SdkStructure {
/** 
此结构体大小,必须赋值
*/
    public			int            dwSize;
/** 
安全等级信息
*/
    public NET_XRAY_MULTILEVEL_DETECT_CFG_INFO stuInfo = new NET_XRAY_MULTILEVEL_DETECT_CFG_INFO();

public NET_IN_SET_XRAY_MULTILEVEL_DETECT_INFO(){
		this.dwSize=this.size();
}
}

