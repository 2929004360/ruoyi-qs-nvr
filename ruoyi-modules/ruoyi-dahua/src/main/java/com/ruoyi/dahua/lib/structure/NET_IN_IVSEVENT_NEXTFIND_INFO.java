package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_IVSEventNextFind接口入参 
* @date 2022/11/02 13:50:37
*/
public class NET_IN_IVSEVENT_NEXTFIND_INFO extends NetSDKLib.SdkStructure {
/** 
此结构体大小,必须赋值
*/
    public			int            dwSize;
/** 
查询起点偏移
*/
    public			int            nStartIndex;
/** 
单次查询返回数量
*/
    public			int            nLimit;

public NET_IN_IVSEVENT_NEXTFIND_INFO(){
		this.dwSize=this.size();
}
}

