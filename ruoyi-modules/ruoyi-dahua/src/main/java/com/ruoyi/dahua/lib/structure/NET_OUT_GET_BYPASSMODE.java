package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  获取旁路状态 输出参数。此时CLIENT_GetAlarmRegionInfo的emType参数为NET_EM_GET_ALARMREGION_INFO_BYPASSMODE 
* @origin autoTool
* @date 2023/08/09 16:33:14
*/
public class NET_OUT_GET_BYPASSMODE extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
防区个数
*/
    public			int            nZoneRet;
/** 
防区工作模式     {@link com.ruoyi.dahua.lib.enumeration.EM_BYPASSMODE_TYPE}
*/
    public			int[]          arrModes = new int[72];
/** 
防区个数扩展
*/
    public			int            nZoneRetEx;
/** 
防区号扩展  超过72使用这个字段  {@link com.ruoyi.dahua.lib.enumeration.EM_BYPASSMODE_TYPE}
*/
    public			int[]          arrModesEx = new int[256];

public NET_OUT_GET_BYPASSMODE(){
		this.dwSize=this.size();
}
}

