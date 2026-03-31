package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  金属锈蚀结果 
* @date 2022/06/28 19:44:57
*/
public class NET_METAL_CORROSION extends NetSDKLib.SdkStructure {
/** 
包围盒
*/
    public NET_RECT         stuBoundingBox = new NET_RECT();
/** 
预留字段
*/
    public			byte[]         bReserved = new byte[128];

public			NET_METAL_CORROSION(){
}
}

