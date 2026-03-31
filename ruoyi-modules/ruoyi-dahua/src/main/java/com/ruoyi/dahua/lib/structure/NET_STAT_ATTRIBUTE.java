package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  属性计数信息 
* @date 2023/02/06 16:07:27
*/
public class NET_STAT_ATTRIBUTE extends NetSDKLib.SdkStructure {
/** 
目标及属性组合的计数
*/
    public			int            nNumber;
/** 
属性组合个数
*/
    public			int            nAttrMapNumber;
/** 
属性组合
*/
    public			NET_STAT_ATTRMAP[] stuAttrMap = new NET_STAT_ATTRMAP[20];
/** 
预留字节
*/
    public			byte[]         szReserved = new byte[128];

public			NET_STAT_ATTRIBUTE(){
		for(int i=0;i<stuAttrMap.length;i++){
			stuAttrMap[i]=new NET_STAT_ATTRMAP();
			}
}
}

