package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 接口 CLIENT_GetClusterState 的输出参数
 * @date 2022/10/09 14:24:34
 */
public class NET_OUT_GET_CLUSTER_STATE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 此结构体大小,必须赋值
	 */
    public int              dwSize;
	/**
	 * 当前聚档状态 1.已完成. 2.进行中 3.聚档失败
	 */
    public int              nClusterState;
	/**
	 * 记录更新时间，已设备本地时间为准
	 */
    public NET_TIME_EX      stuDealTime = new NET_TIME_EX();
	/**
	 * 聚档触发时间, 按照年月日记录, 作为聚档的唯一key, 单设备每日只有一条聚档记录.如20220530则表示在20220530当日已经聚档
	 */
    public int              nClusterData;
	/**
	 * 上次聚档类型: 1.自动每日聚档 2.平台下发手动聚档 3.手动测试触发 4.聚档主动失败重试
	 */
    public int              nClusterType;
	/**
	 * / 聚档信息，主要用于展示失败原因, 正常事件该字段为空或0
	 */
    public int              nClusterMessage;
	/**
	 * / 返回记录ID，单设备ID理论唯一，表示同一次聚档任务
	 */
    public int              nRecordID;

	public NET_OUT_GET_CLUSTER_STATE_INFO() {
		this.dwSize = this.size();
	}
}

