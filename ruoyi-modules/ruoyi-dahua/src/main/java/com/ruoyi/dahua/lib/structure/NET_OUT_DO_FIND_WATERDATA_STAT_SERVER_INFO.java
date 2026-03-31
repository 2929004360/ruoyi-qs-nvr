package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_DoFindWaterDataStatServer 输出参数 
* @date 2022/08/22 17:27:50
*/
public class NET_OUT_DO_FIND_WATERDATA_STAT_SERVER_INFO extends NetSDKLib.SdkStructure {
/** 
此结构体大小,必须赋值
*/
    public			int            dwSize;
/** 
查询到的条数
*/
    public			int            nFound;
/** 
流量统计信息个数
*/
    public			int            nInfoNum;
/** 
流量统计信息
*/
    public			NET_WATERDATA_STAT_SERVER_INFO[] stuInfo = new NET_WATERDATA_STAT_SERVER_INFO[64];

public NET_OUT_DO_FIND_WATERDATA_STAT_SERVER_INFO(){
		for(int i=0;i<stuInfo.length;i++){
			stuInfo[i]=new NET_WATERDATA_STAT_SERVER_INFO();
			}
		this.dwSize=this.size();
}
}

