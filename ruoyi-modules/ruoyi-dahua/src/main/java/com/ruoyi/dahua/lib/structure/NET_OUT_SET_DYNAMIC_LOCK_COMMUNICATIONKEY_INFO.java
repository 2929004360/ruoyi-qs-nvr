package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_SetDynamicLockCommunicationKey 接口出参 
* @date 2022/06/23 10:17:41
*/
public class NET_OUT_SET_DYNAMIC_LOCK_COMMUNICATIONKEY_INFO extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
动态密码锁错误码 {@link com.ruoyi.dahua.lib.enumeration.EM_DYNAMIC_LOCK_ERRORCODE}
*/
    public			int            emErrorCode;

public NET_OUT_SET_DYNAMIC_LOCK_COMMUNICATIONKEY_INFO(){
		this.dwSize=this.size();
}
}

