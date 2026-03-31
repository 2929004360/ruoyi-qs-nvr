package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_SetDynamicLockTempUserID 接口入参 
* @date 2022/06/23 10:24:57
*/
public class NET_IN_SET_DYNAMIC_LOCK_TEMP_USERID_INFO extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
密码锁ID
*/
    public			byte[]         szID = new byte[32];
/**
 临时身份码
 */
    public			byte[]         szTmpUserID = new byte[512];

public NET_IN_SET_DYNAMIC_LOCK_TEMP_USERID_INFO(){
		this.dwSize=this.size();
}
}

