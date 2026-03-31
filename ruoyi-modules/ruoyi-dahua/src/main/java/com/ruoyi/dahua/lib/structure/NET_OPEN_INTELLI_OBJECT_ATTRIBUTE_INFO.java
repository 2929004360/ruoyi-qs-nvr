package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  目标属性数组 
* @date 2023/02/06 14:57:49
*/
public class NET_OPEN_INTELLI_OBJECT_ATTRIBUTE_INFO extends NetSDKLib.SdkStructure {
/** 
属性类型名称
*/
    public			byte[]         szAttrTypeName = new byte[128];
/** 
属性值名称
*/
    public			byte[]         szAttrValueName = new byte[128];

public NET_OPEN_INTELLI_OBJECT_ATTRIBUTE_INFO(){
}
}

