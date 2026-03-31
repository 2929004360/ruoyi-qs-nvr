package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  机动车车型过滤配置 
* @origin autoTool
* @date 2023/05/29 11:40:45
*/
public class NET_VEHICLE_TYPE_INFO extends NetSDKLib.SdkStructure {
/** 
是否过滤启用: TRUE-下发该配置  FALSE-不下发该配置
*/
    public			int            bEnable;
/** 
车型的个数,最大值是64
*/
    public			int            nShowListNums;
/** 
车辆类型
*/
    public			int[]          emVehicleTypeShowList = new int[64];
/** 
保留字节
*/
    public			byte[]         szReserved = new byte[256];

public NET_VEHICLE_TYPE_INFO(){
}
}

