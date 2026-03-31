package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 事件类型EVENT_IVS_NUMBERSTAT(数量统计事件)对应的规则配置
 * @date 2022/09/23 16:15:49
 */
public class CFG_NUMBERSTAT_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 规则名称,不同规则不能重名
	 */
    public byte[]           szRuleName = new byte[128];
	/**
	 * 规则使能
	 */
    public byte             bRuleEnable;
	/**
	 * 保留字段
	 */
    public byte[]           bReserved = new byte[3];
	/**
	 * 相应物体类型个数
	 */
    public int              nObjectTypeNum;
	/**
	 * 相应物体类型列表
	 */
    public MaxNameByteArrInfo[] szObjectTypes = (MaxNameByteArrInfo[])new MaxNameByteArrInfo().toArray(NetSDKLib.MAX_OBJECT_LIST_SIZE);
	/**
	 * 数量上限 单位：个，1~65535
	 */
    public int              nUpperLimit;
	/**
	 * 上报周期 单位：秒，1~65535
	 */
    public int              nPeriod;
	/**
	 * 检测区顶点数
	 */
    public int              nDetectRegionPoint;
	/**
	 * 检测区
	 */
    public NetSDKLib.CFG_POLYGON[] stuDetectRegion = new NetSDKLib.CFG_POLYGON[20];
	/**
	 * 报警联动
	 */
    public NetSDKLib.CFG_ALARM_MSG_HANDLE stuEventHandler = new NetSDKLib.CFG_ALARM_MSG_HANDLE();
	/**
	 * 事件响应时间段
	 */
    public NetSDKLib.TIME_SECTION_WEEK_DAY_10[] stuTimeSection = (NetSDKLib.TIME_SECTION_WEEK_DAY_10[])new NetSDKLib.TIME_SECTION_WEEK_DAY_10().toArray(NetSDKLib.WEEK_DAY_NUM);
	/**
	 * 云台预置点编号 0~65535
	 */
    public int              nPtzPresetId;
	/**
	 * 数量统计类型 支持："Region" 区域类型 "Entrance" 出入口类型
	 * {@link com.ruoyi.dahua.lib.enumeration.CFG_LINK_TYPE}
	 */
    public int              emType;
	/**
	 * 正常运动方向, 按照此方向行驶是正向，反过来是逆行 只对出入口类型有效。第一个点是起点，第二个点是终点，点的坐标归一化到[0,8191]区间。
	 */
    public NetSDKLib.CFG_POLYGON[] stDirection = new NetSDKLib.CFG_POLYGON[2];
	/**
	 * 记录周期，单位：分钟，1~10
	 */
    public int              nRecordInterval;
	/**
	 * 进入数报警阈值,0表示不报警
	 */
    public int              nEnterThreshold;
	/**
	 * 离开数报警阈值,0表示不报警
	 */
    public int              nExitThreshold;
	/**
	 * 滞留数报警阈值,0表示不报警
	 */
    public int              nInsideThreshold;
	/**
	 * 计划ID,仅球机有效,从1开始
	 */
    public int              nPlanID;
	/**
	 * 区域ID,一个预置点可以对应多个区域
	 */
    public int              nAreaID;
	/**
	 * 检测线实际最大个数
	 */
    public int              nMaxDetectLineNum;
	/**
	 * 离开检测线坐标
	 */
    public NetSDKLib.CFG_POLYLINE[] stuDetectLine = new NetSDKLib.CFG_POLYLINE[20];
	/**
	 * 停留超时时间
	 */
    public int              nStayMinDuration;
	/**
	 * 前端视频分析联动
	 */
    public NetSDKLib.CFG_ALARM_MSG_HANDLE stuRemoteEventHandler = new NetSDKLib.CFG_ALARM_MSG_HANDLE();
	/**
	 * 前端视频分析联动响应时间段
	 */
    public NetSDKLib.TIME_SECTION_WEEK_DAY_10[] stuRemoteTimeSection = (NetSDKLib.TIME_SECTION_WEEK_DAY_10[])new NetSDKLib.TIME_SECTION_WEEK_DAY_10().toArray(NetSDKLib.WEEK_DAY_NUM);
    /**
     * 人数去重策略配置,参见结构体定义 {@link CFG_DUPLICATE_REMOVE_INFO}
    */
    public CFG_DUPLICATE_REMOVE_INFO stuDuplicateRemove = new CFG_DUPLICATE_REMOVE_INFO();
    /**
     * 保留字段
    */
    public byte[]           szReserved = new byte[1024];

	public CFG_NUMBERSTAT_INFO() {
		for (int i = 0; i < stuDetectRegion.length; i++) {
			stuDetectRegion[i] = new NetSDKLib.CFG_POLYGON();
		}
		for (int i = 0; i < stuTimeSection.length; i++) {
			stuTimeSection[i] = new NetSDKLib.TIME_SECTION_WEEK_DAY_10();
		}
		for (int i = 0; i < stDirection.length; i++) {
			stDirection[i] = new NetSDKLib.CFG_POLYGON();
		}
		for (int i = 0; i < stuDetectLine.length; i++) {
			stuDetectLine[i] = new NetSDKLib.CFG_POLYLINE();
		}
		for (int i = 0; i < stuRemoteTimeSection.length; i++) {
			stuRemoteTimeSection[i] = new NetSDKLib.TIME_SECTION_WEEK_DAY_10();
		}
	}
}

