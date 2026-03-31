package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @description 订阅统计通道数据入参
 * @date 2022/12/12 11:41:07
 */
public class NET_IN_ATTACH_VIDEOSTAT_STREAM extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 视频通道号
	 */
    public int              nVideoChannel;
	/**
	 * 粒度 {@link com.ruoyi.dahua.lib.enumeration.EM_STAT_GRANULARITY}
	 */
    public int              emGranularity;
	/**
	 * 字节对齐
	 */
    public byte[]           szReserved1 = new byte[4];
	/**
	 * "NumberStat"：人数统计，统计进入离开数量
	 */
    public byte[]           szRuleType = new byte[32];
	/**
	 * 开始时间，表示从什么时间开始统计上报，开始时间，需按周期取整。如推送周期是10分钟一次，那么开始时间，只能为，每个小时的
	 * 第0分，10分、20分、30分、40、分、50分。
	 */
    public byte[]           szStartTime = new byte[20];
	/**
	 * 统计通道数据回调
	 */
    public NetSDKLib.fVideoStatStreamCallBack cbVideoStatStream;
	/**
	 * 用户数据
	 */
    public Pointer          dwUser;
	/**
	 * 与Granularity结合使用，如emGranularity为 EM_GRANULARITY_MINUTE， Cycle
	 * 值为5，表示以5分钟统计周期上报数据
	 */
    public short            nCycle;
	/**
	 * 字节对齐
	 */
    public byte[]           szReserved2 = new byte[2];
	/**
	 * 滞留情况数组大小，如填0则不下发该字段
	 */
    public int              nSendMinStayTimeValidNum;
	/**
	 * 上报时间段内区域人员滞留情况, 表示统计的间隔结果中返回滞留时间大于3秒，大于5秒，大于10秒的记录,单位秒, 不下发时回调仅返回总人数和总滞留时长
	 */
    public int[]            nMinStayTime = new int[8];

	public NET_IN_ATTACH_VIDEOSTAT_STREAM() {
		this.dwSize = this.size();
	}
}

