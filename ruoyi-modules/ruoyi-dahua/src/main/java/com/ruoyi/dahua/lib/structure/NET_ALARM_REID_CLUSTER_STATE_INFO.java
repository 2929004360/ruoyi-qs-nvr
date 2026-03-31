package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 事件类型DH_ALARM_REID_CLUSTER_STATE (聚档状态事件)对应的数据块描述信息
 * @date 2022/10/09 14:30:33
 */
public class NET_ALARM_REID_CLUSTER_STATE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 通道号
	 */
    public int              nChannelID;
	/**
	 * 事件动作,1表示持续性事件开始,2表示持续性事件结束;
	 */
    public int              nAction;
	/**
	 * 事件公共扩展字段结构体
	 */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
	/**
	 * 事件发生的时间
	 */
    public NET_TIME_EX      stuUTC = new NET_TIME_EX();
	/**
	 * 当前聚档状态 1.已完成. 2.进行中 3.聚档失败
	 */
    public int              nClusterState;
	/**
	 * 上次聚档触发时间，按照年月日记录，作为聚档的唯一key，单设备每日只有一条聚档记录.如20220530则表示在20220530当日已经聚档
	 */
    public int              nClusterDate;
	/**
	 * 上次聚档类型: 1.自动每日聚档 2.平台下发手动聚档 3.手动测试触发 4.聚档主动失败重试
	 */
    public int              nClusterType;
	/**
	 * 记录更新时间，已设备本地时间为准
	 */
    public NET_TIME_EX      stuDealTime = new NET_TIME_EX();
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[1020];

	public NET_ALARM_REID_CLUSTER_STATE_INFO() {
	}
}

