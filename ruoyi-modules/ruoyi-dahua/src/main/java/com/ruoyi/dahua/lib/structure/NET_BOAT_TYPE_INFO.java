package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  船只类型过滤配置 
* @origin autoTool
* @date 2023/05/29 11:40:47
*/
public class NET_BOAT_TYPE_INFO extends NetSDKLib.SdkStructure {
/** 
是否启用, 为TRUE则下发船只类型过滤配置，为FALSE则不下发船只类型过滤配置
*/
    public			int            bEnable;
/** 
船只类型的个数,最大值是64
*/
    public			int            nShowListNums;
/** 
船只类型
*/
    public			int[]          emBoatTypeShowList = new int[64];
/** 
保留字节
*/
    public			byte[]         szReserved = new byte[256];

public NET_BOAT_TYPE_INFO(){
}
}

