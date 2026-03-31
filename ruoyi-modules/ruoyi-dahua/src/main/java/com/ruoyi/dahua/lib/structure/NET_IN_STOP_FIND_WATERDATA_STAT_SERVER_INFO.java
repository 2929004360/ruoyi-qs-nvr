package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_StopFindWaterDataStatServer 输入参数 
* @date 2022/08/23 10:33:46
*/
public class NET_IN_STOP_FIND_WATERDATA_STAT_SERVER_INFO extends NetSDKLib.SdkStructure {
/** 
此结构体大小,必须赋值
*/
    public			int            dwSize;
/** 
查询令牌
*/
    public			int            nToken;

public NET_IN_STOP_FIND_WATERDATA_STAT_SERVER_INFO(){
		this.dwSize=this.size();
}
}

