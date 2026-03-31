package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_GetDevStorageSmartValue 接口出参
 * @date 2022/05/30 14:43:22
 */
public class NET_OUT_GET_DEV_STORAGE_SMART_VALUE extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 设备SMART信息个数
	 */
    public int              nCount;
	/**
	 * 设备SMART信息
	 */
    public NET_SMART_VALUE_INFO[] stuValuesInfo = new NET_SMART_VALUE_INFO[64];

	public NET_OUT_GET_DEV_STORAGE_SMART_VALUE() {
		for (int i = 0; i < stuValuesInfo.length; i++) {
			stuValuesInfo[i] = new NET_SMART_VALUE_INFO();
		}
		this.dwSize = this.size();
	}
}

