package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  分组事件联动 
* @date 2022/12/01 16:27:10
*/
public class NET_XRAY_OBJECT_GROUP extends NetSDKLib.SdkStructure {
/** 
分组类型 {@link com.ruoyi.dahua.lib.enumeration.EM_INSIDE_OBJECT_TYPE}
*/
    public			int            emGroupType;
/** 
分组检测使能
*/
    public			int            bGroupEnable;
/** 
分组安全等级 {@link com.ruoyi.dahua.lib.enumeration.EM_GROUP_GRADE_TYPE}
*/
    public			int            emGroupGrade;
/** 
分组物品颜色 {@link com.ruoyi.dahua.lib.enumeration.EM_GROUP_COLOR_TYPE}
*/
    public			int            emGroupColor;
/** 
分组事件联动
*/
    public			NET_XRAY_OBJECT_GROUP_EVENT_HANDLER stuGroupEventHandler = new NET_XRAY_OBJECT_GROUP_EVENT_HANDLER();
/** 
物体
*/
    public			NET_XRAY_OBJECT_INFO[] stuObjects = new NET_XRAY_OBJECT_INFO[64];
/** 
物体个数
*/
    public			int            nObjectsNum;
/** 
自定义分组类型
*/
    public			byte[]         szGrouptype = new byte[32];
/** 
预留
*/
    public			byte[]         szReserved = new byte[224];

public NET_XRAY_OBJECT_GROUP(){
		for(int i=0;i<stuObjects.length;i++){
			stuObjects[i]=new NET_XRAY_OBJECT_INFO();
			}
}
}

