package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 手动选定目标跟踪出参
 * @date 2022/09/14 14:02:06
 */
public class NET_OUT_MSGROUP_TRACK_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;

	public NET_OUT_MSGROUP_TRACK_INFO() {
		this.dwSize = this.size();
	}
}

