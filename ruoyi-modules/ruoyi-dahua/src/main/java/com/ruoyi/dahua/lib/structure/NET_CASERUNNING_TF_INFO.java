package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description tf卡状态
 * @date 2023/05/11 16:03:12
 */
public class NET_CASERUNNING_TF_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 总用量，单位：M
	 */
    public byte[]           szTotal = new byte[32];
	/**
	 * 已用，单位：M
	 */
    public byte[]           szUsed = new byte[32];
	/**
	 * 剩余，单位：M
	 */
    public byte[]           szRemaining = new byte[32];
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[128];

	public NET_CASERUNNING_TF_INFO() {
	}
}

