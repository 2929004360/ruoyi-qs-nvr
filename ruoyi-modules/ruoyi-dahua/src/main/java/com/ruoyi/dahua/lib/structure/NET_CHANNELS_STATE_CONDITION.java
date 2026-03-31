package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  获取通道状态查询条件 
* @date 2022/10/09 11:22:21
*/
public class NET_CHANNELS_STATE_CONDITION extends NetSDKLib.SdkStructure {
/** 
通道类型 {@link com.ruoyi.dahua.lib.enumeration.EM_CHANNELS_STATE_TYPE}
*/
    public			int            emType;
/** 
保留字节
*/
    public			byte[]         byReserved = new byte[1020];

public NET_CHANNELS_STATE_CONDITION(){
}
}

