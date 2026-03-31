package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 贮柜储量检测实时上报的回调
 * @date 2022/10/28 11:34:32
 */
public class NET_VIDEOSTAT_SUMMARY_TANK_CAPACITY_DETECTION extends NetSDKLib.SdkStructure {
	/**
	 * 通道号
	 */
    public int              nChannelID;
	/**
	 * 贮柜当前状态 {@link com.ruoyi.dahua.lib.enumeration.EM_TANK_STATUS}
	 */
    public int              emTankStatus;
	/**
	 * 时间
	 */
    public NET_TIME_EX      stuUTC = new NET_TIME_EX();
	/**
	 * 用料当前占比（百分比）
	 */
    public int              nCurrentRatio;
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[1024];

	public NET_VIDEOSTAT_SUMMARY_TANK_CAPACITY_DETECTION() {
	}
}

