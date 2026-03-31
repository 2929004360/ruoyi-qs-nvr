package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_GetDeviceSerialNo 入参
 * @date 2023/03/15 20:18:02
 */
public class NET_IN_GET_DEVICESERIALNO_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;

	public NET_IN_GET_DEVICESERIALNO_INFO() {
		this.dwSize = this.size();
	}
}

