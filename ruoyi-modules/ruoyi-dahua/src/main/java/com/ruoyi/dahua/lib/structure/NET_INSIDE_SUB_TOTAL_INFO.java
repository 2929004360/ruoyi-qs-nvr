package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 本周期区域内滞留人员统计信息
 * @date 2023/03/19 16:19:34
 */
public class NET_INSIDE_SUB_TOTAL_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 本周期上报的区域内总人数
	 */
    public int              nTotal;
	/**
	 * 当前周期，所有人的总滞留时长
	 */
    public int              nTotalStayTime;
	/**
	 * 人次数据有效个数
	 */
    public int              nManStaySummaryValidNum;
	/**
	 * 人次数据,
	 * 例此处下发的规则为滞留时间大于3,5,10,300秒的规则.此处结果就是滞留时间大于3秒共10人次，大于5秒的6人次，10秒的3人次，300秒的0人次
	 */
    public int[]            nManStaySummary = new int[8];
	/**
	 * 总滞留时长有效个数
	 */
    public int              nTotalStayTimeSummaryValidNum;
	/**
	 * 总滞留时长，单位秒,
	 * 例此处下发的规则为滞留时间大于3,5,10,300秒的规则,此处结果就是滞留时间大于3秒的人员的总滞留时长50秒，大于5秒的48秒，10秒的30秒，300秒的0秒
	 */
    public int[]            nTotalStayTimeSummary = new int[8];

	public NET_INSIDE_SUB_TOTAL_INFO() {
	}
}

