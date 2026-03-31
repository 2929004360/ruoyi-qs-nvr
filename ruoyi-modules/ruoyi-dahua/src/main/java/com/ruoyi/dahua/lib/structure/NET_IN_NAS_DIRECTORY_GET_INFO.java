package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_NASDirectoryGetInfo接口入参 
* @date 2022/09/22 17:26:59
*/
public class NET_IN_NAS_DIRECTORY_GET_INFO extends NetSDKLib.SdkStructure {
/** 
此结构体大小,必须赋值
*/
    public			int            dwSize;
/**
 * 共享文件夹名称, 用于创建设备组件实例, 必须填写
*/
    public  byte[]          szName = new byte[128];

public NET_IN_NAS_DIRECTORY_GET_INFO(){
		this.dwSize=this.size();
}
}

