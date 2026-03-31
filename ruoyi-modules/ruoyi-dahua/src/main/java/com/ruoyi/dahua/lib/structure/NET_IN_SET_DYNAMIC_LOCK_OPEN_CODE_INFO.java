package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_SetDynamicLockOpenCode 接口入参 
* @date 2022/06/23 10:28:36
*/
public class NET_IN_SET_DYNAMIC_LOCK_OPEN_CODE_INFO extends NetSDKLib.SdkStructure {
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
    public			byte[]         szOpenCode = new byte[512];

public NET_IN_SET_DYNAMIC_LOCK_OPEN_CODE_INFO(){
		this.dwSize=this.size();
}
}

