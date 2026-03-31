package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_StartFindWaterDataStatServer 输入参数 
* @date 2022/08/22 17:12:11
*/
public class NET_IN_START_FIND_WATERDATA_STAT_SERVER_INFO extends NetSDKLib.SdkStructure {
/** 
此结构体大小,必须赋值
*/
    public			int            dwSize;
/** 
水质检测数据查询条件
*/
    public			NET_START_FIND_WATERDATA_CONDITION stuCondition = new NET_START_FIND_WATERDATA_CONDITION();

public			NET_IN_START_FIND_WATERDATA_STAT_SERVER_INFO(){
		this.dwSize=this.size();
}
}

