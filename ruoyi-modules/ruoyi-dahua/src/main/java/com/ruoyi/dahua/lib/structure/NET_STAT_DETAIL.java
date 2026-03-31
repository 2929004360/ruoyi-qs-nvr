package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  详细计数信息 
* @date 2023/02/06 16:07:27
*/
public class NET_STAT_DETAIL extends NetSDKLib.SdkStructure {
/** 
目标类型名称
*/
    public			byte[]         szType = new byte[32];
/** 
目标类型索引
*/
    public			int            nTypeID;
/** 
目标类型总数
*/
    public			int            nNumber;
/** 
属性计数信息
*/                    //1024 原生大小
    public			NET_STAT_ATTRIBUTE[] stuAttribute = new NET_STAT_ATTRIBUTE[128]; 
//因为 stuAttribute 原生大小太大，1024个数组大小,使用该字段减少结构体的初始化
    public byte[]           dy = new byte[4136*(1024-128)];
/** 
属性计数信息个数
*/
    public			int            nAttributeNumber;
/** 
预留字节
*/
    public			byte[]         szReserved = new byte[124];

public			NET_STAT_DETAIL(){
		for(int i=0;i<stuAttribute.length;i++){
			stuAttribute[i]=new NET_STAT_ATTRIBUTE();
			}
}
}

