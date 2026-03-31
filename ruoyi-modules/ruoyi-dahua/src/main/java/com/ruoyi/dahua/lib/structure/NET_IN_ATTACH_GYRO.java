package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/** 
* @author 291189
* @description  CLIENT_AttachGyro接口入参 
* @date 2022/10/26 15:21:25
*/
public class NET_IN_ATTACH_GYRO extends NetSDKLib.SdkStructure {
/** 
此结构体大小,必须赋值
*/
    public			int            dwSize;
/** 
回调函数
*/
    public NetSDKLib.fNotifyGyroData cbNotifyGyroData;
/** 
用户自定义参数
*/
    public Pointer          dwUser;

public NET_IN_ATTACH_GYRO(){
		this.dwSize=this.size();
}
}

