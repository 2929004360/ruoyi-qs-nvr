package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_GetZoneArmMode 接口出参 
* @origin autoTool
* @date 2023/08/09 11:16:07
*/
public class NET_OUT_GET_ZONE_ARMODE_INFO extends NetSDKLib.SdkStructure {
/** 
此结构体大小,必须赋值
*/
    public			int            dwSize;
/** 
布撤防模式数组有效个数(第二维)
*/
    public			int            nStateNum;
/** 
布撤防模式:1."T":Total布防/外出布防模式 2."D" 撤防
*/
    public			byte[]         szState = new byte[256*32];

public			NET_OUT_GET_ZONE_ARMODE_INFO(){
		this.dwSize=this.size();
}
}

