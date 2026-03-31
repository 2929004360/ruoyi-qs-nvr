package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  获取旁路状态 输入参数。此时CLIENT_GetAlarmRegionInfo的emType参数为NET_EM_GET_ALARMREGION_INFO_BYPASSMODE 
* @origin autoTool
* @date 2023/08/09 16:27:26
*/
public class NET_IN_GET_BYPASSMODE extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
防区个数
*/
    public			int            nZoneNum;
/** 
防区号, 从1开始
*/
    public			int[]          arrZones = new int[72];
/** 
防区个数扩展
*/
    public			int            nZoneNumEx;
/** 
防区号扩展  超过72使用这个字段
*/
    public			int[]          arrZonesEx = new int[256];

public NET_IN_GET_BYPASSMODE(){
		this.dwSize=this.size();
}
}

