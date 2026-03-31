package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  聚焦设置基本信息单元 
* @date 2022/11/01 11:16:54
*/
public class CFG_VIDEO_IN_FOCUS_UNIT extends NetSDKLib.SdkStructure {
/** 
聚焦模式, 0-关闭, 1-辅助聚焦, 2-自动聚焦, 3-半自动聚焦, 4-手动聚焦
*/
    public			int            nMode;
/** 
聚焦灵敏度, 0-高, 1-默认, 2-低
*/
    public			int            nSensitivity;
/** 
红外光聚焦修正, 0-不修正, 1-修正, 2-自动修正
*/
    public			int            nIRCorrection;
/** 
聚焦极限 {@link com.ruoyi.dahua.lib.enumeration.EM_FOCUS_LIMIT_SELECT_MODE}
*/
    public			int            emFocusMode;
/** 
聚焦极限值, 单位毫米
*/
    public			int            nFocusLimit;

public			CFG_VIDEO_IN_FOCUS_UNIT(){
}
}

