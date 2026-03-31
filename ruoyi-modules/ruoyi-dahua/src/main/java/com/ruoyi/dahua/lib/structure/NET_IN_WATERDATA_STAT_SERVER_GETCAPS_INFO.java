package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_GetWaterDataStatServerCaps 输入参数 
* @date 2022/08/22 10:06:53
*/
public class NET_IN_WATERDATA_STAT_SERVER_GETCAPS_INFO extends NetSDKLib.SdkStructure {
/** 
此结构体大小,必须赋值
*/
    public			int            dwSize;

public NET_IN_WATERDATA_STAT_SERVER_GETCAPS_INFO(){
		this.dwSize=this.size();
}
}

