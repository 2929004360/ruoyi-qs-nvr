package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  表盘检测结果 
* @date 2022/06/28 19:44:55
*/
public class NET_DIAL_DETECT extends NetSDKLib.SdkStructure {
/** 
表盘状态 {@link com.ruoyi.dahua.lib.enumeration.EM_DIAL_STATE}
*/
    public			int            emDialState;
/** 
包围盒
*/
    public NET_RECT         stuBoundingBox = new NET_RECT();

public NET_DIAL_DETECT(){
}
}

