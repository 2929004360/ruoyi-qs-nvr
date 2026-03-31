package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  密码锁上报随机码事件 (对应 DH_ALARM_DYNAMIC_PWDLOCK_UPLOAD_RANDOMCODE) 
* @date 2022/06/23 09:56:06
*/
public class NET_ALARM_DYNAMIC_LOCK_UPLOAD_RANDOMCODE_INFO extends NetSDKLib.SdkStructure {
/** 
通道号
*/
    public			int            nChannelID;
/** 
事件动作, 1:开始 2:停止
*/
    public			int            nAction;
/** 
事件发生的时间,单位秒
*/
    public NET_TIME_EX      stuUTC = new NET_TIME_EX();
/** 
UTC时间对应的毫秒数
*/
    public			int            nUTCMS;
/** 
密码锁ID
*/
    public			byte[]         szID = new byte[32];
/** 
锁设备开锁请求随机数
*/
    public			byte[]         szRandCode = new byte[512];
/** 
开锁密钥版本
*/
    public			byte[]         szOpenKeyVer = new byte[64];
/** 
保留字节
*/
    public			byte[]         szReserved = new byte[1024];

public NET_ALARM_DYNAMIC_LOCK_UPLOAD_RANDOMCODE_INFO(){
}
}

