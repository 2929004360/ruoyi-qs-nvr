package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  接口 CLIENT_AttachHygrothermograph 的输出参数 
* @origin autoTool
* @date 2023/12/02 10:25:56
*/
public class NET_OUT_ATTACH_HYGROTHERMOGRAPH extends NetSDKLib.SdkStructure {
/** 
/ 此结构体大小,必须赋值
*/
    public			int            dwSize;

public			NET_OUT_ATTACH_HYGROTHERMOGRAPH(){
		this.dwSize=this.size();
}
}

