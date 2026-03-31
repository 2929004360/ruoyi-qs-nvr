package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  吸烟检测结果 
* @date 2022/06/28 19:44:56
*/
public class NET_SMOKING_DETECT extends NetSDKLib.SdkStructure {
/** 
包围盒
*/
    public NET_RECT         stuBoundingBox = new NET_RECT();

public			NET_SMOKING_DETECT(){
}
}

