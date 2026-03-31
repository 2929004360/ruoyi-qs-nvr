package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_SetDynamicLockCommunicationKey 接口入参 
* @date 2022/06/23 10:15:55
*/
public class NET_IN_SET_DYNAMIC_LOCK_COMMUNICATIONKEY_INFO extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
密码锁ID
*/
    public			byte[]         szID = new byte[32];
/** 
通讯密钥
*/
    public			byte[]         szCommuKey = new byte[512];
/** 
通讯密钥校验数据
*/
    public			byte[]         szKeyVerify = new byte[512];
/** 
原通讯密钥校验数据
*/
    public			byte[]         szOldKeyVerify = new byte[512];
/** 
加密主机私钥签名
*/
    public			byte[]         szPrivateSigniture = new byte[512];

public NET_IN_SET_DYNAMIC_LOCK_COMMUNICATIONKEY_INFO(){
		this.dwSize=this.size();
}
}

