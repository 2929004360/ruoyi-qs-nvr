package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  获取通道状态 输入参数。此时CLIENT_GetAlarmRegionInfo的emType参数为NET_EM_GET_ALARMREGION_INFO_CHANNELSSTATE 
* @date 2022/10/09 11:22:21
*/
public class NET_IN_GET_CHANNELS_STATE extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
获取通道状态查询条件
*/
    public			NET_CHANNELS_STATE_CONDITION stuCondition = new NET_CHANNELS_STATE_CONDITION();

public NET_IN_GET_CHANNELS_STATE(){
		this.dwSize=this.size();
}
}

