package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 事件类型EVENT_IVS_MAN_NUM_DETECTION(立体视觉区域内人数统计事件)对应的规则配置
 * @date 2022/09/23 16:08:32
 */
public class CFG_IVS_MAN_NUM_DETECTION_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 规则名称,不同规则不能重名
	 */
    public byte[]           szRuleName = new byte[128];
	/**
	 * 规则使能
	 */
    public int              bRuleEnable;
	/**
	 * 触发跟踪使能,仅对绊线，入侵规则有效
	 */
    public byte             bTrackEnable;
	/**
	 * 保留字段
	 */
    public byte[]           bReserved1 = new byte[3];
	/**
	 * 相应物体类型个数
	 */
    public int              nObjectTypeNum;
	/**
	 * 相应物体类型列表
	 */
    public MaxNameByteArrInfo[] szObjectTypes = (MaxNameByteArrInfo[])new MaxNameByteArrInfo().toArray(NetSDKLib.MAX_OBJECT_LIST_SIZE);
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
	 * 检测区域顶点数
	 */
    public int              nDetectRegionPoint;
	/**
	 * 检测区域坐标
	 */
    public NetSDKLib.CFG_POLYGON[] stuDetectRegion = new NetSDKLib.CFG_POLYGON[20];
	/**
	 * 异常人数阈值
	 */
    public int              nThreshold;
	/**
	 * 人数异常检测方式 0-小于等于阈值报警 1-大于等于阈值报警
	 */
    public int              nDetectType;
	/**
	 * null
	 */
    public int              nSensitivity;
	/**
	 * 最大检测高度,单位cm
	 */
    public int              nMaxHeight;
	/**
	 * 最小检测高度,单位cm
	 */
    public int              nMinHeight;
	/**
	 * 是否开启滞留检测报警功能, 对应报警为StayDetection。
	 */
    public int              bStayDetectEnable;
	/**
	 * 最短滞留持续时间，单位秒[2, 65535], 开启滞留检测报警时需设置
	 */
    public int              nStayMinDuration;
	/**
	 * 是否开启人数异常报警功能, 默认为TRUE。对应报警为ManNumDetection
	 */
    public int              bManNumAlarmEnable;
	/**
	 * 区域ID（一个预置点对应多个区域ID）
	 */
    public int              nAreaID;
	/**
	 * 计划ID，范围 1~MaxNumberStatPlan
	 */
    public int              nPlanId;
	/**
	 * 保留字节
	 */
    public byte[]           bReserved = new byte[504];

	public CFG_IVS_MAN_NUM_DETECTION_INFO() {
		for (int i = 0; i < stuTimeSection.length; i++) {
			stuTimeSection[i] = new NetSDKLib.TIME_SECTION_WEEK_DAY_10();
		}
		for (int i = 0; i < stuDetectRegion.length; i++) {
			stuDetectRegion[i] = new NetSDKLib.CFG_POLYGON();
		}
	}
}

