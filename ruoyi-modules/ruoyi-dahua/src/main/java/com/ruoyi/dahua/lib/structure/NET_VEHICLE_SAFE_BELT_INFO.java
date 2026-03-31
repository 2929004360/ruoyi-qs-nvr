package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  安全带过滤配置 
* @origin autoTool
* @date 2023/05/29 11:40:46
*/
public class NET_VEHICLE_SAFE_BELT_INFO extends NetSDKLib.SdkStructure {
/** 
是否过滤启用: TRUE-下发该配置  FALSE-不下发该配置
*/
    public			int            bEnable;
/** 
安全带情况的个数,最大值是2
*/
    public			int            nShowListNums;
/** 
安全带情况
*/
    public			int[]          emVehicleSafeBeltShowList = new int[2];
/** 
保留字节
*/
    public			byte[]         szReserved = new byte[256];

public NET_VEHICLE_SAFE_BELT_INFO(){
}
}

