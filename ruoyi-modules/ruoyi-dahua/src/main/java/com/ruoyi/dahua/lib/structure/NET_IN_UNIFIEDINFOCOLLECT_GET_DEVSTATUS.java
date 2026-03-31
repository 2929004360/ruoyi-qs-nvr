package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description   获取设备状态入参 
* @date 2022/09/01 15:10:37
*/
public class NET_IN_UNIFIEDINFOCOLLECT_GET_DEVSTATUS extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;

public NET_IN_UNIFIEDINFOCOLLECT_GET_DEVSTATUS(){
		this.dwSize=this.size();
}
}

