package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_GetWaterDataStatServerCaps 输出参数 
* @date 2022/08/22 10:11:26
*/
public class NET_OUT_WATERDATA_STAT_SERVER_GETCAPS_INFO extends NetSDKLib.SdkStructure {
/** 
此结构体大小,必须赋值
*/
    public			int            dwSize;
/** 
当前是否具备水质检测功能 {@link com.ruoyi.dahua.lib.enumeration.EM_WATERDATA_STAT_SERVER_SUPPORT}
*/
    public			int            emSupport;
/** 
是否支持本地存储 {@link com.ruoyi.dahua.lib.enumeration.EM_SUPPORT_LOCALDATA_STORE}
*/
    public			int            emSupportLocalDataStore;

public NET_OUT_WATERDATA_STAT_SERVER_GETCAPS_INFO(){
		this.dwSize=this.size();
}
}

