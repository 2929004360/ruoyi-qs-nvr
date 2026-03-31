package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  每个屏蔽区的测温屏蔽区配置信息 
* @date 2022/11/07 10:53:23
*/
public class THERMOMETRY_SHIELD_INFO extends NetSDKLib.SdkStructure {
/** 
屏蔽使能, TRUE开启, FALSE关闭
*/
    public			int            bEnable;
/** 
屏蔽区编号
*/
    public			int            nShieldId;
/** 
自定义名称
*/
    public			byte[]         szName = new byte[64];
/**
 坐标点
*/
    public NET_RECT         stuRect = new NET_RECT();
/** 
预置点编号
*/
    public			int            nPresetId;
/** 
预留字段
*/
    public			byte[]         szReserved = new byte[128];

public THERMOMETRY_SHIELD_INFO(){
}
}

