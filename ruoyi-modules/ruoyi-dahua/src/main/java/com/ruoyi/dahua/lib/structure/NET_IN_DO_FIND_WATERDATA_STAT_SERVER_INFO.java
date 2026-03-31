package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_DoFindWaterDataStatServer 输入参数 
* @date 2022/08/22 17:27:08
*/
public class NET_IN_DO_FIND_WATERDATA_STAT_SERVER_INFO extends NetSDKLib.SdkStructure {
/** 
此结构体大小,必须赋值
*/
    public			int            dwSize;
/** 
查询令牌
*/
    public			int            nToken;
/** 
查询起始序号, 表示从beginNumber条记录开始,取count条记录返回
*/
    public			int            nBeginNumber;
/** 
每次查询的流量统计条数
*/
    public			int            nCount;

public NET_IN_DO_FIND_WATERDATA_STAT_SERVER_INFO(){
		this.dwSize=this.size();
}
}

