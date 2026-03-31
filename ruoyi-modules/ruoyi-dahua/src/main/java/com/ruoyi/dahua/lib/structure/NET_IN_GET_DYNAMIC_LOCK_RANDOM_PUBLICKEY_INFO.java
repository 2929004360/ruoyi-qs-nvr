package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_GetDynamicLockRandomPublicKey 接口入参 
* @date 2022/06/23 10:08:09
*/
public class NET_IN_GET_DYNAMIC_LOCK_RANDOM_PUBLICKEY_INFO extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
密码锁ID
*/
    public			byte[]         szID = new byte[32];
/** 
中心公钥
*/
    public			byte[]         szCenterPublicKey = new byte[512];

public NET_IN_GET_DYNAMIC_LOCK_RANDOM_PUBLICKEY_INFO(){
		this.dwSize=this.size();
}
}

