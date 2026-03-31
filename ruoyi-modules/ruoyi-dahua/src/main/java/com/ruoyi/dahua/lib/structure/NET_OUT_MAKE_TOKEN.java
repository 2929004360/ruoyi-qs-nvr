package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description   获取token出参 
* @date 2022/10/12 11:10:49
*/
public class NET_OUT_MAKE_TOKEN extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
Token
*/
    public			int            nToken;

public			NET_OUT_MAKE_TOKEN(){
		this.dwSize=this.size();
}
}

