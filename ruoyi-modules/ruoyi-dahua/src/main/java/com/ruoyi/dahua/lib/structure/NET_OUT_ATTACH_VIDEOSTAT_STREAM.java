package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 订阅统计通道数据出参
 * @date 2022/12/12 11:39:32
 */
public class NET_OUT_ATTACH_VIDEOSTAT_STREAM extends NetSDKLib.SdkStructure {
	/**
	 * 此结构体大小
	 */
    public int              dwSize;

	public NET_OUT_ATTACH_VIDEOSTAT_STREAM() {
		this.dwSize = this.size();
	}
}

