package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 统计通道数据信息
 * @date 2022/12/12 13:43:42
 */
public class NET_CB_VIDEOSTAT_STREAM extends NetSDKLib.SdkStructure {
	/**
	 * 统计通道号
	 */
    public int              nChannel;
	/**
	 * 预置点编号默认值为0，球机必填，范围1~PtzPresetMax，IPC可不填。
	 */
    public int              nPtzPresetId;
	/**
	 * 规则名称
	 */
    public byte[]           szRuleNanme = new byte[128];
	/**
	 * 当前这一统计周期的开始时间
	 */
    public byte[]           szStartTime = new byte[20];
	/**
	 * "NumberStat"：人数统计
	 */
    public byte[]           szRuleType = new byte[32];
	/**
	 * 粒度 {@link com.ruoyi.dahua.lib.enumeration.EM_STAT_GRANULARITY}
	 */
    public int              emStatGranularity;
	/**
	 * 与Granularity结合使用，如emGranularity为 EM_GRANULARITY_MINUTE， Cycle
	 * 值为5，表示以5分钟统计周期上报数据
	 */
    public short            nCycle;
	/**
	 * 区域ID，没有该字段或者为0，为单区域
	 */
    public short            nAreaID;
	/**
	 * 本周期内进入人数小计
	 */
    public int              nEnteredSubtotal;
	/**
	 * 本周期内离开人数小计
	 */
    public int              nExitedSubtotal;
	/**
	 * 本周期内经过人数小计
	 */
    public int              nPassedSubtotal;
	/**
	 * 本周期区域内滞留人员统计信息, bIsSendMinStayTime为TRUE时有效
	 */
    public NET_INSIDE_SUB_TOTAL_INFO stuInsideSubtotal = new NET_INSIDE_SUB_TOTAL_INFO();
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[940];

	public NET_CB_VIDEOSTAT_STREAM() {
	}
}

