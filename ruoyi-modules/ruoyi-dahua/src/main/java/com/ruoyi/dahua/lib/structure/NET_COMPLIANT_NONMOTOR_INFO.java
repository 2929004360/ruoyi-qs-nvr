package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  非机动车过滤配置 
* @origin autoTool
* @date 2023/05/29 11:40:46
*/
public class NET_COMPLIANT_NONMOTOR_INFO extends NetSDKLib.SdkStructure {
/** 
非机动车颜色过滤条件
*/
    public			NET_NONMOTOR_COLOR_INFO stuNonMotorColor = new NET_NONMOTOR_COLOR_INFO();
/** 
车上人数过滤条件
*/
    public			NET_NONMOTOR_CYCLING_NUM_INFO stuNumOfCycling = new NET_NONMOTOR_CYCLING_NUM_INFO();
/** 
非机动车类型过滤条件
*/
    public			NET_NONMOTOR_CATEGORY_INFO stuCategory = new NET_NONMOTOR_CATEGORY_INFO();
/** 
保留字节
*/
    public			byte[]         szReserved = new byte[1024];

public			NET_COMPLIANT_NONMOTOR_INFO(){
}
}

