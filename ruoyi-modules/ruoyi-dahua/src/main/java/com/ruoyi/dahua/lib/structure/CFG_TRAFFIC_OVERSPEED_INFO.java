package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @description 事件类型 EVENT_IVS_TRAFFIC_OVERSPEED (交通违章-超速)对应的规则配置
 * @date 2022/09/07 15:13:00
 */
public class CFG_TRAFFIC_OVERSPEED_INFO extends NetSDKLib.SdkStructure {
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
    public byte[]           szObjectTypes = new byte[16 * 128];
	/**
	 * 云台预置点编号 0~65535
	 */
    public int              nPtzPresetId;
	/**
	 * 车道编号
	 */
    public int              nLane;
	/**
	 * 速度上限
	 */
    public int              nSpeedUpperLimit;
	/**
	 * 速度下限
	 */
    public int              nSpeedLowerLimit;
	/**
	 * 最短触发时间 单位：秒
	 */
    public int              nMinDuration;
	/**
	 * 报警联动
	 */
    public NetSDKLib.CFG_ALARM_MSG_HANDLE stuEventHandler = new NetSDKLib.CFG_ALARM_MSG_HANDLE();
	/**
	 * 事件响应时间段
	 */
    public NetSDKLib.CFG_TIME_SECTION[] stuTimeSection = new NetSDKLib.CFG_TIME_SECTION[7 * 10];
	/**
	 * 是否启用大小车限速
	 */
    byte                    bSpeedLimitForSize;
	/**
	 * 保留字段
	 */
    public byte[]           bReserved1 = new byte[3];
	/**
	 * 小型车速度上限
	 */
    public int              nSmallCarSpeedUpperLimit;
	/**
	 * 小型车速度下限
	 */
    public int              nSmallCarSpeedLowerLimit;
	/**
	 * 大车限低速宽限值
	 */
    public int              nBigCarUnderSpeedMargin;
	/**
	 * 大车限高速宽限值
	 */
    public int              nBigCarOverSpeedMargin;
	/**
	 * 小车限低速宽限值
	 */
    public int              nUnderSpeedMargin;
	/**
	 * 小车限高速宽限值
	 */
    public int              nOverSpeedMargin;
	/**
	 * 大车车速度上限
	 */
    public int              nBigCarSpeedUpperLimit;
	/**
	 * 大车车速度下限
	 */
    public int              nBigCarSpeedLowerLimit;
	/**
	 * 黄牌车限速上限
	 */
    public int              nYellowSpeedUpperLimit;
	/**
	 * 黄牌车限速下限
	 */
    public int              nYellowSpeedLowerLimit;
	/**
	 * 是否抓拍摩托车
	 */
    public byte             bSnapMotorcycle;
	/**
	 * 是否抓拍无牌机动车,0为不抓拍无牌机动车,1为抓拍无牌机动车,默认为0
	 */
    public byte             bSnapNoPlateMotor;
	/**
	 * 保留字段
	 */
    public byte[]           bReserved2 = new byte[2];

	public CFG_TRAFFIC_OVERSPEED_INFO() {
		for (int i = 0; i < stuTimeSection.length; i++) {
			stuTimeSection[i] = new NetSDKLib.CFG_TIME_SECTION();
		}
	}
}

