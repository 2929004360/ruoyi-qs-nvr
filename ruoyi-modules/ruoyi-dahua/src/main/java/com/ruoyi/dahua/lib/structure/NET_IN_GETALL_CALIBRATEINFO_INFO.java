package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 获取所有标定点信息入参
 * @date 2023/05/24 10:24:52
 */
public class NET_IN_GETALL_CALIBRATEINFO_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 通道号
	 */
    public int              nChannel;

	public NET_IN_GETALL_CALIBRATEINFO_INFO() {
		this.dwSize = this.size();
	}
}

