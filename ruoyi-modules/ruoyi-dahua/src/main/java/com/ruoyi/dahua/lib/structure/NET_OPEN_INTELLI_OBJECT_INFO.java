package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  检测到的目标属性信息列表 
* @date 2023/02/06 14:57:49
*/
public class NET_OPEN_INTELLI_OBJECT_INFO extends NetSDKLib.SdkStructure {
/** 
目标id
*/
    public			int            nObjectId;
/** 
包围盒 矩形类型,8192坐标系
*/
    public NET_RECT         stuBoundingBox = new NET_RECT();
/** 
目标类型名称
*/
    public			byte[]         szObjectTypeName = new byte[128];
/** 
目标属性数组中的有效个数
*/
    public			int            nObjectAttributeNums;
/** 
目标属性数组
*/
    public			NET_OPEN_INTELLI_OBJECT_ATTRIBUTE_INFO[] stuObjectAttributes = new NET_OPEN_INTELLI_OBJECT_ATTRIBUTE_INFO[128];

public NET_OPEN_INTELLI_OBJECT_INFO(){
		for(int i=0;i<stuObjectAttributes.length;i++){
			stuObjectAttributes[i]=new NET_OPEN_INTELLI_OBJECT_ATTRIBUTE_INFO();
			}
}
}

