package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  透雾设置配置信息,对应结构体 CFG_VIDEOINDEFOG 
* @origin autoTool
* @date 2023/11/30 15:49:08
*/
public class CFG_VIDEOINDEFOG extends NetSDKLib.SdkStructure {
/** 
透雾模式 {@link com.ruoyi.dahua.lib.enumeration.CFG_DEFOG_MODE}
*/
    public			int            emDefogMode;
/** 
强度(0-100)
*/
    public			int            nIntensity;
/** 
大气光模式 {@link com.ruoyi.dahua.lib.enumeration.CFG_LIGHT_INTENSITY_MODE}
*/
    public			int            emLightIntensityMode;
/** 
大气光强度(0-15)
*/
    public			int            nLightIntensityLevel;
/** 
光学去雾使能 (TRUE使能，FALSE去使能)
*/
    public			int            bCamDefogEnable;

public CFG_VIDEOINDEFOG(){
}
}

