package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  接口(CLIENT_StartFindFluxStat)输出参数 
* @date 2022/05/07 10:00:48
*/
public class NET_OUT_TRAFFICSTARTFINDSTAT extends NetSDKLib.SdkStructure {
/** 
此结构体大小
*/
    public			int            dwSize;
/** 
符合此次查询条件的结果总条数
*/
    public			int            dwTotalCount;

public NET_OUT_TRAFFICSTARTFINDSTAT(){
		this.dwSize=this.size();
}
}

