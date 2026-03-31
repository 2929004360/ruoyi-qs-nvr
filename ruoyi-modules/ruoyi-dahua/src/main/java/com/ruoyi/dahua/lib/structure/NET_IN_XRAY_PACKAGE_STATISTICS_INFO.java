package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description  包裹信息
 * @date 2021/7/1
 */
public class NET_IN_XRAY_PACKAGE_STATISTICS_INFO extends NetSDKLib.SdkStructure {
    public  byte[]          szUUID = new byte[36];                // UUID
    public  int             nStatisticsInfoID;                    // 统计信息ID
    public NET_TIME_EX      stuBeginTime = new NET_TIME_EX();     // 开始时间
    public NET_TIME_EX      stuEndTime = new NET_TIME_EX();       // 结束时间
    public int              nTotalCount;                          // 包裹总数
    public  int             nStatisticsInfoByGradeNum;            // 按危险等级统计的信息的数量
    public NET_IN_PACKAGE_STATISTICS_BYGRADE[] stuStatisticsInfoByGrade = new NET_IN_PACKAGE_STATISTICS_BYGRADE[16]; // 按危险等级统计的信息
    public int              nStatisticsInfoByTypeNum;             // 按危险类型统计的信息的数量
    public NET_IN_PACKAGE_STATISTICS_BYTYPE[] stuStatisticsInfoByType = new NET_IN_PACKAGE_STATISTICS_BYTYPE[64]; // 按危险类型统计的信息
    public byte[]           byReserved = new byte[1024];          // 保留字段
}

