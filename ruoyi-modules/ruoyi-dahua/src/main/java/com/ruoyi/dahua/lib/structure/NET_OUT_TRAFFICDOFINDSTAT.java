package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  接口(CLIENT_DoFindFluxStat)输出参数 
* @date 2022/05/07 10:04:12
*/
public class NET_OUT_TRAFFICDOFINDSTAT extends NetSDKLib.SdkStructure {
/** 
此结构体大小
*/
    public			int            dwSize;
/** 
统计信息指针
*/
    public			DH_TRAFFICFLOWSTAT_OUT stStatInfo = new DH_TRAFFICFLOWSTAT_OUT();

public NET_OUT_TRAFFICDOFINDSTAT(){
		this.dwSize=this.size();
}
}

