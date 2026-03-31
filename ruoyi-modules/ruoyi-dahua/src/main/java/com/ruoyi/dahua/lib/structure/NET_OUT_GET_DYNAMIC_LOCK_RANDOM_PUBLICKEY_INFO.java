package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_GetDynamicLockRandomPublicKey 接口出参 
* @date 2022/06/23 10:09:19
*/
public class NET_OUT_GET_DYNAMIC_LOCK_RANDOM_PUBLICKEY_INFO extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
动态密码锁错误码 {@link com.ruoyi.dahua.lib.enumeration.EM_DYNAMIC_LOCK_ERRORCODE}
*/
    public			int            emErrorCode;
/** 
锁随机公钥
*/
    public			byte[]         szRandomPublicKey = new byte[512];

public NET_OUT_GET_DYNAMIC_LOCK_RANDOM_PUBLICKEY_INFO(){
		this.dwSize=this.size();
}
}

