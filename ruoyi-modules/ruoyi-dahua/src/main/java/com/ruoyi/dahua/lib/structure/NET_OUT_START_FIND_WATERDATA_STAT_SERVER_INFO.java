package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_StartFindWaterDataStatServer 输出参数 
* @date 2022/08/22 17:14:16
*/
public class NET_OUT_START_FIND_WATERDATA_STAT_SERVER_INFO extends NetSDKLib.SdkStructure {
/** 
此结构体大小,必须赋值
*/
    public			int            dwSize;
/** 
取到的查询令牌
*/
    public			int            nToken;
/** 
符合此次查询条件的结果总条数
*/
    public			int            nTotalCount;

public NET_OUT_START_FIND_WATERDATA_STAT_SERVER_INFO(){
		this.dwSize=this.size();
}
}

