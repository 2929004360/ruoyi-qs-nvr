package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  船只合规报警过滤配置 
* @origin autoTool
* @date 2023/05/29 11:40:47
*/
public class NET_COMPLIANT_BOAT_INFO extends NetSDKLib.SdkStructure {
/** 
船只类型过滤配置
*/
    public			NET_BOAT_TYPE_INFO stuBoatType = new NET_BOAT_TYPE_INFO();
/** 
保留字节
*/
    public			byte[]         szReserved = new byte[1024];

public NET_COMPLIANT_BOAT_INFO(){
}
}

