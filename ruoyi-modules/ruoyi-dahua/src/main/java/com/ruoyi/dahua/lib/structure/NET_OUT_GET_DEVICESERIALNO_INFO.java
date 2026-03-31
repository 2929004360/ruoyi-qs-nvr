package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_GetDeviceSerialNo 出参
 * @date 2023/03/15 20:18:12
 */
public class NET_OUT_GET_DEVICESERIALNO_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 序列号
	 */
    public byte[]           szSN = new byte[64];

	public NET_OUT_GET_DEVICESERIALNO_INFO() {
		this.dwSize = this.size();
	}
}

