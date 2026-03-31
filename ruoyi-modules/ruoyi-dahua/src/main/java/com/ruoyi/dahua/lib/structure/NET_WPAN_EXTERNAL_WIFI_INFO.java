package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  外部wifi信息 
* @date 2022/08/31 14:44:17
*/
public class NET_WPAN_EXTERNAL_WIFI_INFO extends NetSDKLib.SdkStructure {
/** 
外部wifi使能
*/
    public			int            bEnable;
/** 
外部wifi优先级 {@link com.ruoyi.dahua.lib.enumeration.EM_EXTERNAL_WIFI_PRIORITY}
*/
    public			int            emPriority;
/** 
预留字段
*/
    public			byte[]         byReserved = new byte[32];

public			NET_WPAN_EXTERNAL_WIFI_INFO(){
}
}

