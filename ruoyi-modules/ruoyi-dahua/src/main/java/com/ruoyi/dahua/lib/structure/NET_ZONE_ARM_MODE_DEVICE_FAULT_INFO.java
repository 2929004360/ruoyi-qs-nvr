package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 设备异常信息
 * @origin autoTool
 * @date 2023/08/10 09:52:29
 */
public class NET_ZONE_ARM_MODE_DEVICE_FAULT_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 设备名称
	 */
    public byte[]           szName = new byte[32];
	/**
	 * 异常原因
	 */
    public byte[]           szReason = new byte[32];
	/**
	 * 保留字节
	 */
    public byte[]           szResvered = new byte[1024];

	public NET_ZONE_ARM_MODE_DEVICE_FAULT_INFO() {
	}
}

