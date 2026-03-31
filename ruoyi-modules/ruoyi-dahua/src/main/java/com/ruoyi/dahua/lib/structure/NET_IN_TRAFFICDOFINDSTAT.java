package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  接口(CLIENT_DoFindFluxStat)输入参数 
* @date 2022/05/07 10:03:35
*/
public class NET_IN_TRAFFICDOFINDSTAT extends NetSDKLib.SdkStructure {
/** 
此结构体大小
*/
    public			int            dwSize;
/** 
每次查询的流量统计条数
*/
    public			int            nCount;
/** 
等待接收数据的超时时间
*/
    public			int            nWaittime;

public NET_IN_TRAFFICDOFINDSTAT(){
		this.dwSize=this.size();
}
}

