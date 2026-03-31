package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  等级配置 
* @date 2022/12/01 16:27:10
*/
public class NET_MULTI_LEVEL_INFO extends NetSDKLib.SdkStructure {
/** 
配置等级 {@link com.ruoyi.dahua.lib.enumeration.EM_XRAY_OBJECT_DETECT_CONFIG_LEVEL}
*/
    public			int            emLevel;
/** 
默认参考等级，该等级的默认配置与参考等级的默认配置一样 {@link com.ruoyi.dahua.lib.enumeration.EM_XRAY_OBJECT_DETECT_CONFIG_LEVEL}
*/
    public			int            emDefaultReferenceLevel;
/** 
等级名字
*/
    public			byte[]         szName = new byte[128];
/** 
物品分组数组
*/
    public			NET_XRAY_OBJECT_GROUP[] stuObjectGroups = new NET_XRAY_OBJECT_GROUP[64];
/** 
物品分组数组个数
*/
    public			int            nObjectGroupsNum;
/** 
侧视角物品分组数组个数
*/
    public			int            nSlaveViewObjectGroupsNum;
/** 
侧视角物品分组数组
*/
    public			NET_XRAY_OBJECT_GROUP[] stuSlaveViewObjectGroups = new NET_XRAY_OBJECT_GROUP[64];
/** 
预留
*/
    public			byte[]         szReserved = new byte[512];

public NET_MULTI_LEVEL_INFO(){
		for(int i=0;i<stuObjectGroups.length;i++){
			stuObjectGroups[i]=new NET_XRAY_OBJECT_GROUP();
			}
		for(int i=0;i<stuSlaveViewObjectGroups.length;i++){
			stuSlaveViewObjectGroups[i]=new NET_XRAY_OBJECT_GROUP();
			}
}
}

