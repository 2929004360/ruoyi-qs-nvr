package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  假日计划能力 
* @origin autoTool
* @date 2023/02/10 14:34:20
*/
public class NET_SPECIAL_DAYS_SCHEDULE_CAPS extends NetSDKLib.SdkStructure {
/** 
是否支持假日计划
*/
    public			int            bSupport;
/** 
设备支持的最大计划数量
*/
    public			int            nMaxSpecialDaysSchedules;
/** 
每天最多的时间段
*/
    public			int            nMaxTimePeriodsPerDay;
/** 
设备支持的最大假日组数
*/
    public			int            nMaxSpecialDayGroups;
/** 
每个假日组里最大的假日数
*/
    public			int            nMaxDaysInSpecialDayGroup;
/** 
保留字节
*/
    public			byte[]         byReserved = new byte[128];

public NET_SPECIAL_DAYS_SCHEDULE_CAPS(){
}
}

