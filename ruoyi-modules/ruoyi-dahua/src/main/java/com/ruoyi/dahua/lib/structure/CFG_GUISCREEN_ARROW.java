package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  诱导屏箭头设置 
* @origin autoTool
* @date 2023/06/27 15:49:59
*/
public class CFG_GUISCREEN_ARROW extends NetSDKLib.SdkStructure {
/** 
箭头指向 {@link com.ruoyi.dahua.lib.enumeration.EM_DIRECTION}
*/
    public			int            emDirect;
/** 
箭头相对于数字的位置 {@link com.ruoyi.dahua.lib.enumeration.EM_GUIDESCREEN_POS}
*/
    public			int            emPos;
/** 
是否滚动 {@link com.ruoyi.dahua.lib.enumeration.EM_GUIDESCREEN_ROLL}
*/
    public			int            emRoll;

public			CFG_GUISCREEN_ARROW(){
}
}

