package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

import static com.ruoyi.dahua.lib.NetSDKLib.NET_GATEWAY_MAX_SIM_NUM;

/** 
* @author 291189
* @description   网络相关信息 
* @date 2022/08/31 14:35:19
*/
public class NET_DEVSTATUS_NET_INFO extends NetSDKLib.SdkStructure {
/** 
wifi信号强度等级，0~5，0表示没有信号
*/
    public			int            nWifiIntensity;
/** 
wifi信号强度，单位dbm, 0~100,0表示没有信号
*/
    public			int            nWifiSignal;
/** 
2g/3g/4g信号强度,单位dbm. 0~100, 0表示没有信号
*/
    public			int            nCellulSignal;
/** 
2g/3g/4g信号强度等级,0~5, 0表示没有信号
*/
    public			int            nCellulIntensity;
/** 
有线网连接状态 {@link com.ruoyi.dahua.lib.enumeration.NET_EM_ETH_STATE}
*/
    public			int            emEthState;
/** 
蜂窝网络实际使用流量，单位：MB
*/
    public			int            n3Gflux;
/** 
网络实际使用时长，单位：分钟
*/
    public			int            n3GfluxByTime;
/** 
网络连接状态 {@link com.ruoyi.dahua.lib.enumeration.NET_EM_ETH_STATE}
*/
    public			int            emWifiState;
/** 
蜂窝网络连接状态 {@link com.ruoyi.dahua.lib.enumeration.NET_EM_ETH_STATE}
*/
    public			int            emCellularstate;
/** 
SIM卡数量
*/
    public			int            nSimNum;
/** 
SIM卡状态信息
*/
    public			NET_DEVSTATUS_SIM_INFO[] stuSimInfo = new NET_DEVSTATUS_SIM_INFO[NET_GATEWAY_MAX_SIM_NUM];

public			NET_DEVSTATUS_NET_INFO(){
		for(int i=0;i<stuSimInfo.length;i++){
			stuSimInfo[i]=new NET_DEVSTATUS_SIM_INFO();
			}
}
}

