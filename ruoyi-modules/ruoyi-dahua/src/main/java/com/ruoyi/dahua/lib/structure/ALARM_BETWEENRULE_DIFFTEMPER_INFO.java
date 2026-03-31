package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 热成像规则间温差异常报警 (DH_ALARM_BETWEENRULE_TEMP_DIFF)
 * @date 2023/05/24 10:24:56
 */
public class ALARM_BETWEENRULE_DIFFTEMPER_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 0:开始 1:停止
	 */
    public int              nAction;
	/**
	 * 温差OSD配置中的OSD编号
	 */
    public int              nOsdId;
	/**
	 * 视频通道号
	 */
    public int              nChannelID;
	/**
	 * 报警条件 0:小于 1:等于 2:大于
	 * {@link com.ruoyi.dahua.lib.enumeration.NET_RADIOMETRY_ALARMCONTION}
	 */
    public int              emAlarmContion;
	/**
	 * 测温规则1,参考配置CFG_RADIOMETRY_RULE_INFO
	 */
    public int              nRuleId1;
	/**
	 * 测温规则2,参考配置CFG_RADIOMETRY_RULE_INFO
	 */
    public int              nRuleId2;
	/**
	 * 规则间温差值
	 */
    public float            fDiffValue;
	/**
	 * 温度单位(当前配置的温度单位),见 NET_TEMPERATURE_UNIT
	 */
    public int              nTemperatureUnit;
	/**
	 * 预置点
	 */
    public int              nPresetID;
	/**
	 * 事件公共扩展字段结构体
	 */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
	/**
	 * 预留字段
	 */
    public byte[]           reserved = new byte[128];

	public ALARM_BETWEENRULE_DIFFTEMPER_INFO() {
	}
}

