package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  非机动车颜色过滤条件 
* @origin autoTool
* @date 2023/05/29 11:40:46
*/
public class NET_NONMOTOR_COLOR_INFO extends NetSDKLib.SdkStructure {
/** 
是否启用, 为TRUE则下发非机动车颜色过滤条件，为FALSE则不下发非机动车颜色过滤条件
*/
    public			int            bEnable;
/** 
非机动车车身颜色的个数,最大值是32
*/
    public			int            nShowListNums;
/** 
非机动车车身颜色
*/
    public			int[]          emNonMotorColorShowList = new int[32];
/** 
保留字节
*/
    public			byte[]         szReserved = new byte[256];

public			NET_NONMOTOR_COLOR_INFO(){
}
}

