package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  按钮信息 
* @date 2022/08/31 14:44:18
*/
public class NET_WPAN_ACCESSORY_BUTTON_INFO extends NetSDKLib.SdkStructure {
/** 
按键使能
*/
    public			int            bEnable;
/** 
报警类型 {@link com.ruoyi.dahua.lib.enumeration.EM_BUTTON_ALARM_TYPE}
*/
    public			int            emType;
/** 
警号联动个数
*/
    public			int            nSirenLinkageNum;
/** 
警号联动
*/
    public			int[]          nSirenLinkage = new int[64];
/** 
预留字段
*/
    public			byte[]         byReserved = new byte[32];

public			NET_WPAN_ACCESSORY_BUTTON_INFO(){
}
}

