package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_IVSEventFind接口出参 
* @date 2022/11/02 13:48:02
*/
public class NET_OUT_IVSEVENT_FIND_INFO extends NetSDKLib.SdkStructure {
/** 
此结构体大小,必须赋值
*/
    public			int            dwSize;
/** 
查询到结果总数
*/
    public			int            dwCount;

public NET_OUT_IVSEVENT_FIND_INFO(){
		this.dwSize=this.size();
}
}

