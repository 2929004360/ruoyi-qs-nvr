package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_GetDeviceEthBandInfo 接口出参
 * @date 2022/05/30 14:45:57
 */
public class NET_OUT_GET_DEVICE_ETH_BAND_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 网卡信息个数
	 */
    public int              nCount;
	/**
	 * 网卡信息
	 */
    public NET_BAND_SPEED_INFO[] stuBandSpeedInfo = new NET_BAND_SPEED_INFO[10];

	public NET_OUT_GET_DEVICE_ETH_BAND_INFO() {
		for (int i = 0; i < stuBandSpeedInfo.length; i++) {
			stuBandSpeedInfo[i] = new NET_BAND_SPEED_INFO();
		}
		this.dwSize = this.size();
	}
}

