package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  获取通道状态 输出参数。此时CLIENT_GetAlarmRegionInfo的emType参数为NET_EM_GET_ALARMREGION_INFO_CHANNELSSTATE 
* @date 2022/10/09 11:24:36
*/
public class NET_OUT_GET_CHANNELS_STATE extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
通道状态个数
*/
    public			int            nChannelsStatesCount;
/** 
通道状态
*/
    public			NET_CHANNELS_STATE[] stuChannelsStates = new NET_CHANNELS_STATE[1024];

public NET_OUT_GET_CHANNELS_STATE(){
		for(int i=0;i<stuChannelsStates.length;i++){
			stuChannelsStates[i]=new NET_CHANNELS_STATE();
			}
		this.dwSize=this.size();
}
}

