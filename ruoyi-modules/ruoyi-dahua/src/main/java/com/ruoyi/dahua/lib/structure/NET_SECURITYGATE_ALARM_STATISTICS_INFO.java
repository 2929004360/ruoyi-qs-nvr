package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description 安全门报警统计信息
 * @date 2021/6/29
 */
public class NET_SECURITYGATE_ALARM_STATISTICS_INFO extends NetSDKLib.SdkStructure {
    public byte[]           szUUID = new byte[36];                // UUID
    public int              nStatisticsInfoID;                    // 统计信息ID
    public NET_TIME_EX      stuBeginTime = new NET_TIME_EX();     // 开始时间
    public NET_TIME_EX      stuEndTime = new NET_TIME_EX();       // 结束时间
    public int              nPassIn;                              // 进入通过人数
    public int              nAlarmIn;                             // 进入报警人数
    public int              nPassOut;                             // 离开通过人数
    public int              nAlarmOut;                            // 离开报警人数
    public int              nTempNormalCount;                     // 体温正常人数
    public int              nTempAlarmCount;                      // 体温异常人数
    public int              nClassifyResultStatisticsCount;       // 分类检测结果人数统计信息个数
    public NET_CLASSIFY_RESULT_STATISTICS_INFO[] stuClassifyResultStatistics = new NET_CLASSIFY_RESULT_STATISTICS_INFO[32]; // 分类检测结果人数统计信息
    public byte[]           byReserved = new byte[1024];          //保留字段

    public NET_SECURITYGATE_ALARM_STATISTICS_INFO() {
    	for (int i = 0; i < stuClassifyResultStatistics.length; i++) {
    		stuClassifyResultStatistics[i] = new NET_CLASSIFY_RESULT_STATISTICS_INFO();
		}
    }
}

