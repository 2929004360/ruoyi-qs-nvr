package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  心跳信息 
* @date 2022/08/31 14:44:17
*/
public class NET_WPAN_HEARTBEAT_INFO extends NetSDKLib.SdkStructure {
/** 
心跳间隔，单位：秒
*/
    public			int            nInterval;
/** 
离线次数
*/
    public			int            nOfflineTimes;
/** 
预留字段
*/
    public			byte[]         byReserved = new byte[32];

public			NET_WPAN_HEARTBEAT_INFO(){
}
}

