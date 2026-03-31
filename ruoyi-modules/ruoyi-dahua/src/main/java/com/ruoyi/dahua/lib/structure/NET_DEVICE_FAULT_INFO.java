package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 设备异常信息
 * @date 2023/03/15 16:26:00
 */
public class NET_DEVICE_FAULT_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 设备名称
	 */
    public byte[]           szName = new byte[32];
	/**
	 * 异常原因，平台直接取值做显示即可
	 */
    public byte[]           szReason = new byte[32];
	/**
	 * 保留字节
	 */
    public byte[]           byReserved = new byte[512];

	public NET_DEVICE_FAULT_INFO() {
	}
}

