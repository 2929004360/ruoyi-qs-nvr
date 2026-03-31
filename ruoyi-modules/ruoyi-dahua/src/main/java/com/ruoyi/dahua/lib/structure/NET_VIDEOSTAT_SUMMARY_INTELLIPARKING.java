package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 立体行为-智能停车实时数据
 * @date 2022/10/28 11:30:56
 */
public class NET_VIDEOSTAT_SUMMARY_INTELLIPARKING extends NetSDKLib.SdkStructure {
	/**
	 * 时间
	 */
    public NET_TIME_EX      UTC = new NET_TIME_EX();
	/**
	 * 通道号
	 */
    public int              nChannelID;
	/**
	 * 预置点编号
	 */
    public int              nPtzPresetId;
	/**
	 * 数据类型 {@link com.ruoyi.dahua.lib.enumeration.NET_EM_INTELLIPARKING_DATA_TYPE}
	 */
    public int              emType;
	/**
	 * 规划式停车场车位信息统计,当emType为NET_EM_INTELLIPARKING_DATA_TYPE_PLANAREA时有效
	 */
    public NET_INTELLIPARKING_PLANAREA_INFO stPlanArea = new NET_INTELLIPARKING_PLANAREA_INFO();
	/**
	 * 开放式停车场信息统计,当emType为NET_EM_INTELLIPARKING_DATA_TYPE_OPENAREA时有效
	 */
    public NET_INTELLIPARKING_OPENAREA_INFO stOpenArea = new NET_INTELLIPARKING_OPENAREA_INFO();
	/**
	 * 保留字节
	 */
    public byte[]           reserved = new byte[1024];

	public NET_VIDEOSTAT_SUMMARY_INTELLIPARKING() {
	}
}

