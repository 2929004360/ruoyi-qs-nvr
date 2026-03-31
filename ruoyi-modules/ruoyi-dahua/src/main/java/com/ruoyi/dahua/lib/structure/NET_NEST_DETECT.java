package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  鸟巢检测结果 
* @date 2022/06/28 19:44:54
*/
public class NET_NEST_DETECT extends NetSDKLib.SdkStructure {
/** 
包围盒
*/
    public NET_RECT         stuBoundingBox = new NET_RECT();

public NET_NEST_DETECT(){
}
}

