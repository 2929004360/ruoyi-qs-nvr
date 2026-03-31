package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 获取所有标定信息出参
 * @date 2023/05/24 10:24:53
 */
public class NET_OUT_GETALL_CALIBRATEINFO_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * GPS信息
	 */
    public NET_DEVLOCATION_INFO stuGPSInfo = new NET_DEVLOCATION_INFO();
	/**
	 * 标定信息
	 */
    public NET_LOCATION_CALIBRATE_INFO stuLocationCalibrateInfo = new NET_LOCATION_CALIBRATE_INFO();

	public NET_OUT_GETALL_CALIBRATEINFO_INFO() {
		this.dwSize = this.size();
	}
}

