package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 事件类型EVENT_IVS_INTELLI_SHELF(智能补货事件)对应的数据块描述信息
 * @date 2022/09/19 14:46:49
 */
public class DEV_EVENT_INTELLI_SHELF_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 通道号
	 */
    public int              nChannelID;
	/**
	 * 0:脉冲 1:开始 2:停止
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
	 * 智能事件所属大类 {@link NetSDKLib.EM_CLASS_TYPE}
	 */
    public int              emClassType;
	/**
	 * 区域ID（从1开始）, 0为单区域
	 */
    public int              nAreaID;
	/**
	 * 事件触发的预置点号，从1开始
	 */
    public int              nPresetID;
	/**
	 * 表示货架上剩余货物的百分比
	 */
    public int              nPercent;
	/**
	 * 全景大图
	 */
    public NetSDKLib.SCENE_IMAGE_INFO_EX stuSceneImage = new NetSDKLib.SCENE_IMAGE_INFO_EX();
	/**
	 * 预留字节用于字节对齐
	 */
    public byte[]           byReserved1 = new byte[4];
	/**
	 * 预留字节
	 */
    public byte[]           byReserved = new byte[1024];

	public DEV_EVENT_INTELLI_SHELF_INFO() {
	}
}

