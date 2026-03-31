package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 立体行为-视频统计摘要信息
 * @date 2022/10/28 11:06:44
 */
public class NET_VIDEOSTAT_SUMMARY_STEREO extends NetSDKLib.SdkStructure {
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
	 * 数据类型 {@link com.ruoyi.dahua.lib.enumeration.NET_EM_VS_DATA_TYPE}
	 */
    public int              emType;
	/**
	 * 目标实时检测位置统计
	 */
    public NET_VIDEOSTAT_INSIDESUBTOTAL_INFO stuInsideSubtotal = new NET_VIDEOSTAT_INSIDESUBTOTAL_INFO();
	/**
	 * 保留字节
	 */
    public byte[]           reserved = new byte[1024];

	public NET_VIDEOSTAT_SUMMARY_STEREO() {
	}
}

