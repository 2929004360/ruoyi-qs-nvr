package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 获取视频统计摘要信息结构体
 * @date 2022/12/19 09:10:21
 */
public class CFG_VIDEOSATA_SUMMARY_INFO extends NetSDKLib.SdkStructure {
    public int              nStructSize;
	/**
	 * 统计通道号
	 */
    public int              nChannelID;
	/**
	 * 规则名称
	 */
    public byte[]           szRuleName = new byte[128];
	/**
	 * 统计时间，转换到UTC
	 */
    public CFG_NET_TIME_EX  stuStatTime = new CFG_NET_TIME_EX();
	/**
	 * 进入总计
	 */
    public int              nEnteredTotal;
	/**
	 * 今天进入总计
	 */
    public int              nEnteredToday;
	/**
	 * 本月进入总计
	 */
    public int              nEnteredMonth;
	/**
	 * 今年进入总计
	 */
    public int              nEnteredYear;
	/**
	 * 每日最大进入总计
	 */
    public int              nEnteredDaily;
	/**
	 * 出去总计
	 */
    public int              nExitedTotal;
	/**
	 * 今天出去总计
	 */
    public int              nExitedToday;
	/**
	 * 本月出去总计
	 */
    public int              nExitedMonth;
	/**
	 * 今年出去总计
	 */
    public int              nExitedYear;
	/**
	 * 每日最大出去总计
	 */
    public int              nExitedDaily;
	/**
	 * 平均所有保有统计(除去零值)
	 */
    public int              nAvgTotal;
	/**
	 * 平均今天保有统计
	 */
    public int              nAvgToday;
	/**
	 * 平均本月保有统计
	 */
    public int              nAvgMonth;
	/**
	 * 平均今年保有统计
	 */
    public int              nAvgYear;
	/**
	 * 最大所有保有统计(除去零值)
	 */
    public int              nMaxTotal;
	/**
	 * 最大今天保有统计
	 */
    public int              nMaxToday;
	/**
	 * 最大本月保有统计
	 */
    public int              nMaxMonth;
	/**
	 * 最大今年保有统计
	 */
    public int              nMaxYear;
	/**
	 * 区域内人数
	 */
    public int              nInsideSubTotal;
	/**
	 * 规则类型 {@link com.ruoyi.dahua.lib.enumeration.EM_CFG_RULE_TYPE}
	 */
    public int              emRuleType;
	/**
	 * 离开人员的数量
	 */
    public int              nRetExitManNum;
	/**
	 * 离开人员的滞留时间信息
	 */
    public CFG_EXITMAN_STAY_STAT[] stuExitManStayInfo = new CFG_EXITMAN_STAY_STAT[32];
	/**
	 * 设备运行后猪只统计总数,重启后从上次总数开始继续累加
	 */
    public int              nEnteredTotalPig;
	/**
	 * 小时内的总猪只数量
	 */
    public int              nEnteredHourPig;
	/**
	 * 当天的总猪只数(自然天)
	 */
    public int              nEnteredTodayPig;
	/**
	 * IPC专用，如果不执行clearSectionStat操作，等同于TodayPig猪只数
	 */
    public int              nEnteredTotalPigInTimeSection;
	/**
	 * 设备运行后猪只统计总数,重启后从上次总数开始继续累加
	 */
    public int              nExitedTotalPig;
	/**
	 * 小时内的总猪只数量
	 */
    public int              nExitedHourPig;
	/**
	 * 当天的总猪只数(自然天)
	 */
    public int              nExitedTodayPig;
	/**
	 * IPC专用，如果不执行clearSectionStat操作，等同于TodayPig猪只数
	 */
    public int              nExitedTotalPigInTimeSection;
	/**
	 * 区域内猪只数
	 */
    public int              nInsideTotalPig;
	/**
	 * 猪只离开滞留时间信息个数
	 */
    public int              nInsidePigStayStatCount;
	/**
	 * 猪只离开滞留时间信息
	 */
    public CFG_PIG_STAY_STAT[] stuInsidePigStayStatInfo = new CFG_PIG_STAY_STAT[32];
	/**
	 * 当天的猪只数
	 */
    public int              nInsideTodayPig;
	/**
	 * 自动清零时间段内的人数（IPC专用，如果不执行clearSectionStat操作，等同于Today人数）
	 */
    public int              nEnteredTotalInTimeSection;
	/**
	 * 自动清零时间段内的人数（IPC专用，如果不执行clearSectionStat操作，等同于Today人数）
	 */
    public int              nExitedTotalInTimeSection;

	public CFG_VIDEOSATA_SUMMARY_INFO() {
		this.nStructSize = this.size();
		for (int i = 0; i < stuExitManStayInfo.length; i++) {
			stuExitManStayInfo[i] = new CFG_EXITMAN_STAY_STAT();
		}
		for (int i = 0; i < stuInsidePigStayStatInfo.length; i++) {
			stuInsidePigStayStatInfo[i] = new CFG_PIG_STAY_STAT();
		}
	}
}

