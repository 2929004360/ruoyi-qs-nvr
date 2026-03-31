package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  合规报警属性过滤规则 
* @origin autoTool
* @date 2023/05/29 11:40:47
*/
public class NET_COMPLIANT_FORMAT_INFO extends NetSDKLib.SdkStructure {
/** 
为TRUE: 下发人体过滤配置, 为FALSE: 不下发人体过滤配置
*/
    public			int            bSupportHuman;
/** 
人体过滤配置，包括人体和目标的属性
*/
    public			NET_COMPLIANT_HUMAN_INFO stuHuman = new NET_COMPLIANT_HUMAN_INFO();
/** 
为TRUE: 下发机动车合规报警过滤配置, 为FALSE: 不下发机动车合规报警过滤配置
*/
    public			int            bSupportVehicle;
/** 
机动车合规报警过滤配置
*/
    public			NET_COMPLIANT_VEHICLE_INFO stuVehicle = new NET_COMPLIANT_VEHICLE_INFO();
/** 
为TRUE: 下发非机动车过滤配置, 为FALSE: 不下发非机动车过滤配置
*/
    public			int            bSupportNonMotor;
/** 
非机动车过滤配置
*/
    public			NET_COMPLIANT_NONMOTOR_INFO stuNonMotor = new NET_COMPLIANT_NONMOTOR_INFO();
/** 
船只合规报警过滤配置
*/
    public			NET_COMPLIANT_BOAT_INFO stuBoat = new NET_COMPLIANT_BOAT_INFO();
/** 
保留字节
*/
    public			byte[]         szReserved = new byte[1024];

public NET_COMPLIANT_FORMAT_INFO(){
}
}

