package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_SetDynamicLockOpenKey 接口入参 
* @date 2022/06/23 10:20:45
*/
public class NET_IN_SET_DYNAMIC_LOCK_OPENKEY_INFO extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
密码锁ID
*/
    public			byte[]         szID = new byte[32];
/** 
开锁密钥
*/
    public			byte[]         szOpenKey = new byte[512];
/** 
开锁密钥校验
*/
    public			byte[]         szKeyVerify = new byte[512];
/** 
开锁密钥版本
*/
    public			byte[]         szKeyVersion = new byte[512];

public NET_IN_SET_DYNAMIC_LOCK_OPENKEY_INFO(){
		this.dwSize=this.size();
}
}

