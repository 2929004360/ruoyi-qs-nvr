package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  密码锁闭锁事件 (对应 DH_ALARM_DYNAMIC_PWDLOCK_CLOSE) 
* @date 2022/06/23 10:03:38
*/
public class NET_ALARM_DYNAMIC_PWDLOCK_CLOSE_INFO extends NetSDKLib.SdkStructure {
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
闭锁码
*/
    public			byte[]         szCloseCode = new byte[512];
/** 
保留字节
*/
    public			byte[]         szReserved = new byte[576];

public NET_ALARM_DYNAMIC_PWDLOCK_CLOSE_INFO(){
}
}

