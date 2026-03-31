package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  门禁AC服务协议能力获取入参 
* @origin autoTool
* @date 2023/02/10 14:33:29
*/
public class NET_IN_AC_CAPS extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;

public NET_IN_AC_CAPS(){
		this.dwSize=this.size();
}
}

