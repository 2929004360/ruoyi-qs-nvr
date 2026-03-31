package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  水质检测数据查询条件 
* @date 2022/08/22 17:12:11
*/
public class NET_START_FIND_WATERDATA_CONDITION extends NetSDKLib.SdkStructure {
/** 
开始时间
*/
    public NET_TIME_EX      stuStartTime = new NET_TIME_EX();
/** 
检测类型   {@link com.ruoyi.dahua.lib.enumeration.EM_WATER_DETECTION_ALARM_TYPE}
*/
    public			int[]          emType = new int[32];
/** 
检测类型个数
*/
    public			int            nTypeNum;
/** 
预置点个数
*/
    public			int            nPresetIDNum;
/** 
预置点
*/
    public			int[]          nPresetID = new int[32];
/** 
结束时间
*/
    public NET_TIME_EX      stuEndTime = new NET_TIME_EX();
/** 
预留字节
*/
    public			byte[]         szReserved = new byte[256];

public			NET_START_FIND_WATERDATA_CONDITION(){
}
}

