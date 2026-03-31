package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 事件类型EVENT_ALARM_SMARTMOTION_HUMAN(智能视频移动侦测事件(人))对应的数据块描述信息
 * @date 2023/05/15 13:47:59
 */
public class DEV_EVENT_SMARTMOTION_HUMAN_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 通道号
	 */
    public int              nChannelID;
	/**
	 * 1:开始 2:停止
	 */
    public int              nAction;
	/**
	 * 事件名称
	 */
    public byte[]           szName = new byte[128];
	/**
	 * 时间戳(单位是毫秒)
	 */
    public double           PTS;
	/**
	 * 事件发生的时间
	 */
    public NET_TIME_EX      UTC = new NET_TIME_EX();
	/**
	 * 事件ID
	 */
    public int              nEventID;
	/**
	 * 智能动检区域信息
	 */
    public NET_MOTIONDETECT_REGION_INFO[] stuSmartRegion = new NET_MOTIONDETECT_REGION_INFO[32];
	/**
	 * 智能动检区域个数
	 */
    public int              nSmartRegionNum;
	/**
	 * 智能动检(人)对象个数
	 */
    public int              nHumanObjectNum;
	/**
	 * 智能动检(人)对象信息
	 */
    public NET_SMARTDETECT_HUMAN_OBJECT[] stuHumanObject = new NET_SMARTDETECT_HUMAN_OBJECT[64];
	/**
	 * 事件公共扩展字段结构体
	 */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
	/**
	 * 保留字节
	 */
    public byte[]           bReserved = new byte[1024];

	public DEV_EVENT_SMARTMOTION_HUMAN_INFO() {
		for (int i = 0; i < stuSmartRegion.length; i++) {
			stuSmartRegion[i] = new NET_MOTIONDETECT_REGION_INFO();
		}
		for (int i = 0; i < stuHumanObject.length; i++) {
			stuHumanObject[i] = new NET_SMARTDETECT_HUMAN_OBJECT();
		}
	}
}

