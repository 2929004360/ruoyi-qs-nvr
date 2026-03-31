package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  登录失败的锁定信息 
* @date 2022/08/31 14:44:17
*/
public class NET_WPAN_ACCESSORY_LOCK_INFO extends NetSDKLib.SdkStructure {
/** 
登录锁定使能
*/
    public			int            bLockLoginEnable;
/** 
登录失败锁定时间，单位：秒
*/
    public			int            nLoginFailLockTime;
/** 
登录失败可尝试次数
*/
    public			byte           byLockLoginTimes;
/** 
预留字段
*/
    public			byte[]         byReserved = new byte[31];

public			NET_WPAN_ACCESSORY_LOCK_INFO(){
}
}

