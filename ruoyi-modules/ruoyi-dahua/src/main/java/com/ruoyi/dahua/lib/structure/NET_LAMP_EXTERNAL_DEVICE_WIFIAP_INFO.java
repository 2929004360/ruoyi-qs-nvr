package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description WifiAp设备
 * @date 2023/05/11 14:09:05
 */
public class NET_LAMP_EXTERNAL_DEVICE_WIFIAP_INFO extends NetSDKLib.SdkStructure {
	/**
	 * WifiAp设备ID
	 */
    public byte[]           szId = new byte[32];
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[256];

	public NET_LAMP_EXTERNAL_DEVICE_WIFIAP_INFO() {
	}
}

