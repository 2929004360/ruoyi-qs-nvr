package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 报警联动
 * @date 2023/03/15 19:18:13
 */
public class CFG_ALARM_SUBSYSTEM_MSG_HANDLE extends NetSDKLib.SdkStructure {
	/**
	 * 报警输出使能
	 */
    public int              bAlarmOutEnable;
	/**
	 * 警号输出使能
	 */
    public int              bAlarmBellEnable;
	/**
	 * 报警输出通道号个数
	 */
    public int              nAlarmOutChannelNum;
	/**
	 * 报警输出通道号列表
	 */
    public int[]            nAlarmOutChannels = new int[256];

	public CFG_ALARM_SUBSYSTEM_MSG_HANDLE() {
	}
}

