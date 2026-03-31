package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  属性组合 
* @date 2023/02/06 16:07:27
*/
public class NET_STAT_ATTRMAP extends NetSDKLib.SdkStructure {
/** 
属性索引
*/
    public			int            nAttrTypeID;
/** 
属性值索引
*/
    public			int            nValueID;
/** 
属性名称
*/
    public			byte[]         szAttrType = new byte[32];
/** 
属性值
*/
    public			byte[]         szValue = new byte[32];
/** 
预留字节
*/
    public			byte[]         szReserved = new byte[128];

public			NET_STAT_ATTRMAP(){
}
}

