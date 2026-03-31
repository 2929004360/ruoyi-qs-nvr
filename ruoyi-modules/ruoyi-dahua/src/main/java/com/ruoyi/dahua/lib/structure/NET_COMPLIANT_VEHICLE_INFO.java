package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  机动车合规报警过滤配置 
* @origin autoTool
* @date 2023/05/29 11:40:46
*/
public class NET_COMPLIANT_VEHICLE_INFO extends NetSDKLib.SdkStructure {
/** 
机动车车色过滤配置
*/
    public			NET_VEHICLE_COLOR_INFO stuVehicleColor = new NET_VEHICLE_COLOR_INFO();
/** 
车标过滤配置
*/
    public			NET_VEHICLE_BRAND_INFO stuVehicleBrand = new NET_VEHICLE_BRAND_INFO();
/** 
机动车车型过滤配置
*/
    public			NET_VEHICLE_TYPE_INFO stuVehicleType = new NET_VEHICLE_TYPE_INFO();
/** 
车牌颜色过滤配置
*/
    public			NET_VEHICLE_PLATE_COLOR_INFO stuVehiclePlateColor = new NET_VEHICLE_PLATE_COLOR_INFO();
/** 
打电话过滤配置
*/
    public			NET_VEHICLE_CALLING_INFO stuVehicleCalling = new NET_VEHICLE_CALLING_INFO();
/** 
安全带过滤配置
*/
    public			NET_VEHICLE_SAFE_BELT_INFO stuVehicleSafeBelt = new NET_VEHICLE_SAFE_BELT_INFO();
/** 
车上挂件过滤条件
*/
    public			NET_VEHICLE_ATTACHMENT_INFO stuVehicleAttachment = new NET_VEHICLE_ATTACHMENT_INFO();
/** 
保留字节
*/
    public			byte[]         szReserved = new byte[1024];

public			NET_COMPLIANT_VEHICLE_INFO(){
}
}

