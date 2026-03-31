package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 251823
* @description  当前时间点上报车位ID的使用情况 
* @date 2022/10/28 11:30:56
*/
public class NET_INTELLIPARKING_PLANAREA_IDINFO extends NetSDKLib.SdkStructure {
/** 
车位对应的ID
*/
    public			int            nID;
/** 
车位ID对应的使用情况
*/
    public			int            nIsUsed;
/** 
保留字节
*/
    public			byte[]         reserved = new byte[512];

public			NET_INTELLIPARKING_PLANAREA_IDINFO(){
}
}

