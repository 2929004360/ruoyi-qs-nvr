package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  平台下发轮询配置出参 
* @date 2022/07/04 10:58:54
*/
public class NET_OUT_SET_POLLING_CONFIG extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;

public NET_OUT_SET_POLLING_CONFIG(){
		this.dwSize=this.size();
}
}

