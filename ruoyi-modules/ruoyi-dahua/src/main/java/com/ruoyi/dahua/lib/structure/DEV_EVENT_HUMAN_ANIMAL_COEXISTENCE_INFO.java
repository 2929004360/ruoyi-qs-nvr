package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 事件类型 EVENT_IVS_HUMAN_ANIMAL_COEXISTENCE (人和动物检测事件)对应的数据块描述信息
 * @date 2022/12/19 11:22:21
 */
public class DEV_EVENT_HUMAN_ANIMAL_COEXISTENCE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 通道号
	 */
    public int              nChannelID;
	/**
	 * 事件动作, 1:开始 2:停止
	 */
    public int              nAction;
	/**
	 * 事件名称
	 */
    public byte[]           szName = new byte[128];
	/**
	 * 时间戳(单位是毫秒)
	 */
    public double           dbPTS;
	/**
	 * 事件发生的时间
	 */
    public NET_TIME_EX      stuUTC = new NET_TIME_EX();
	/**
	 * 事件时间毫秒数
	 */
    public int              nUTCMS;
	/**
	 * 智能事件所属大类 {EM_SCENE_CLASS_TYPE}
	 */
    public int              emClassType;
	/**
	 * 规则ID, 用于标示哪个规则触发的事件
	 */
    public int              nRuleID;
	/**
	 * 事件组ID，同一物体抓拍过程内GroupID相同
	 */
    public int              nGroupID;
	/**
	 * 一个事件组内的抓拍张数
	 */
    public int              nCountInGroup;
	/**
	 * 一个事件组内的抓拍序号, 从1开始
	 */
    public int              nIndexInGroup;
	/**
	 * 事件ID
	 */
    public int              nEventID;
	/**
	 * 事件触发的预置点号，从1开始, 0 表示无效
	 */
    public int              nPresetID;
	/**
	 * 事件报警类型 {@link com.ruoyi.dahua.lib.enumeration.EM_HUMAN_ANIMAL_ALARM_TYPE}
	 */
    public int              emAlarmType;
	/**
	 * 场景类型 {@link com.ruoyi.dahua.lib.enumeration.EM_DETECTION_SCENE_TYPE}
	 */
    public int              emDetectionSceneType;
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[1024];

	public DEV_EVENT_HUMAN_ANIMAL_COEXISTENCE_INFO() {
	}
}

