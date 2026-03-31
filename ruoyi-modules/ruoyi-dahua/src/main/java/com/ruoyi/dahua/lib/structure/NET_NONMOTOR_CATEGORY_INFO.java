package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  非机动车类型过滤条件 
* @origin autoTool
* @date 2023/05/29 11:40:46
*/
public class NET_NONMOTOR_CATEGORY_INFO extends NetSDKLib.SdkStructure {
/** 
是否启用, 为TRUE则下发非机动车类型过滤条件，为FALSE则不下发非机动车类型过滤条件
*/
    public			int            bEnable;
/** 
非机动车类型的个数,最大值是32
*/
    public			int            nShowListNums;
/** 
过滤出对应的非机动车类型
*/
    public			int[]          emNonMotorTypeShowList = new int[32];
/** 
保留字节
*/
    public			byte[]         szReserved = new byte[256];

public			NET_NONMOTOR_CATEGORY_INFO(){
}
}

