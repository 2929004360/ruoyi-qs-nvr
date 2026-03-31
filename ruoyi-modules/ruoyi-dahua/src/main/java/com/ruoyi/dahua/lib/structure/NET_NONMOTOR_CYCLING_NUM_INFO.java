package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  车上人数过滤条件 
* @origin autoTool
* @date 2023/05/29 11:40:46
*/
public class NET_NONMOTOR_CYCLING_NUM_INFO extends NetSDKLib.SdkStructure {
/** 
是否启用, 为TRUE则下发车上人数过滤条件，为FALSE则不下发车上人数过滤条件
*/
    public			int            bEnable;
/** 
需要过滤出对应的人体数目
*/
    public			int            nCyclingNum;
/** 
保留字节
*/
    public			byte[]         szReserved = new byte[256];

public			NET_NONMOTOR_CYCLING_NUM_INFO(){
}
}

