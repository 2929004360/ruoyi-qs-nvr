package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @description 接口(CLIENT_StartFindFluxStat)输入参数
 * @date 2022/05/07 09:59:47
 */
public class NET_IN_TRAFFICSTARTFINDSTAT extends NetSDKLib.SdkStructure {
	/**
	 * 此结构体大小
	 */
    public int              dwSize;
	/**
	 * 开始时间 暂时精确到小时
	 */
    public NET_TIME         stStartTime = new NET_TIME();
	/**
	 * 结束时间 暂时精确到小时
	 */
    public NET_TIME         stEndTime = new NET_TIME();
	/**
	 * 等待接收数据的超时时间
	 */
    public int              nWaittime;
	/**
	 * 查询的通道号个数
	 */
    public int              nChannelCount;
	/**
	 * 查询的通道号
	 */
    public int[]            nChannels = new int[256];
	/**
	 * 查询的车道号个数
	 */
    public int              nLaneCount;
	/**
	 * 查询的车道号
	 */
    public int[]            nLanes = new int[16];
	/**
	 * 数据库表类型 0表示视频结构化,1表示道路监控
	 */
    public int              nClassType;
	/**
	 * 查询要求返回的统计信息粒度 默认按小时
	 * {@link com.ruoyi.dahua.lib.enumeration.EM_GRANULARITY_STARTFIND_TYPE}
	 */
    public int              emGranularity;
	/**
	 * 统计方向查询条件 {@link com.ruoyi.dahua.lib.enumeration.EM_STARTFIND_DIRECTION}
	 */
    public int              emDirection;

	public NET_IN_TRAFFICSTARTFINDSTAT() {
		this.dwSize = this.size();
	}
}

