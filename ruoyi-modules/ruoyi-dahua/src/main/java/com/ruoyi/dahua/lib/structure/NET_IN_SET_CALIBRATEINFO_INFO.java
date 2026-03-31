package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 设置标定点信息入参
 * @date 2023/05/24 10:24:52
 */
public class NET_IN_SET_CALIBRATEINFO_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 标定点信息
	 */
    public NET_GET_CALIBRATEINFO_POINT_INFO stuPointInfo = new NET_GET_CALIBRATEINFO_POINT_INFO();

	public NET_IN_SET_CALIBRATEINFO_INFO() {
		this.dwSize = this.size();
	}
}

