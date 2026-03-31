package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 事件类型 EVENT_IVS_OBJECT_ABNORMAL 目标异常检测事件 对应的数据块描述信息
 * @date 2022/12/19 11:30:59
 */
public class DEV_EVENT_OBJECT_ABNORMAL_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 通道号
	 */
    public int              nChannelID;
	/**
	 * 0-脉冲 1-开始 2-结束
	 */
    public int              nAction;
	/**
	 * 事件名称
	 */
    public byte[]           szName = new byte[128];
	/**
	 * 时间戳(单位:毫秒)
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
	 * 全景大图信息
	 */
    public NET_EVENT_IMAGE_OFFSET_INFO stuSceneImage = new NET_EVENT_IMAGE_OFFSET_INFO();
	/**
	 * 智能事件所属大类 {@link com.ruoyi.dahua.lib.enumeration.EM_CLASS_TYPE}
	 */
    public int              emClassType;
	/**
	 * 区域ID(一个预置点可以对应多个区域ID)
	 */
    public short            nAreaID;
	/**
	 * 预置点编号,从1开始
	 */
    public short            nPresetID;
	/**
	 * 目标信息
	 */
    public NET_OBJECT_INFO[] stuObjectInfo = new NET_OBJECT_INFO[64];
	/**
	 * 目标信息个数
	 */
    public int              nObjectInfoNum;
	/**
	 * 目标类型 {@link com.ruoyi.dahua.lib.enumeration.EM_ABNORMAL_OBJECT_TYPE}
	 */
    public int              emObjectType;
	/**
	 * 异常行为 {@link com.ruoyi.dahua.lib.enumeration.EM_OBJECT_ABNORMAL_TYPE}
	 */
    public int              emAbnormalType;
	/**
	 * 保留字节
	 */
    public byte[]           bReserved = new byte[1020];

	public DEV_EVENT_OBJECT_ABNORMAL_INFO() {
		for (int i = 0; i < stuObjectInfo.length; i++) {
			stuObjectInfo[i] = new NET_OBJECT_INFO();
		}
	}
}

