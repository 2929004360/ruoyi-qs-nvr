package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 关闭主从式跟踪器实例出参
 * @date 2022/09/14 14:04:38
 */
public class NET_OUT_MSGROUP_CLOSE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;

	public NET_OUT_MSGROUP_CLOSE_INFO() {
		this.dwSize = this.size();
	}
}

