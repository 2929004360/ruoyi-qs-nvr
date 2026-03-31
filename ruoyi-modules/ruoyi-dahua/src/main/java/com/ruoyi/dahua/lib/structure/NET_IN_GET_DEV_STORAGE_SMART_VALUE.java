package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_GetDevStorageSmartValue 接口入参
 * @date 2022/05/30 14:42:47
 */
public class NET_IN_GET_DEV_STORAGE_SMART_VALUE extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 存储设备名称
	 */
    public byte[]           szName = new byte[64];

	public NET_IN_GET_DEV_STORAGE_SMART_VALUE() {
		this.dwSize = this.size();
	}
}

