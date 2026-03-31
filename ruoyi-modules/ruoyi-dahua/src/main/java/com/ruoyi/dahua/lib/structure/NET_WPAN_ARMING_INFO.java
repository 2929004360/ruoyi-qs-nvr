package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  布防信息 
* @date 2022/08/31 14:44:18
*/
public class NET_WPAN_ARMING_INFO extends NetSDKLib.SdkStructure {
/** 
布防模式 {@link com.ruoyi.dahua.lib.enumeration.EM_ARMING_TYPE}
*/
    public			int            emType;
/** 
布防开关：布防时候是否使能该探测器
*/
    public			int            bEnable;
/** 
延时使能：布防时候是否使用进入退出延时
*/
    public			int            bDelayEnable;
/** 
预留字段
*/
    public			byte[]         byReserved = new byte[32];

public			NET_WPAN_ARMING_INFO(){
}
}

