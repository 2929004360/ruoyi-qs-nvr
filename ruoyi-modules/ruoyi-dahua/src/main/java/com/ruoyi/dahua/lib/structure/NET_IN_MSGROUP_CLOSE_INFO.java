package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 关闭主从式跟踪器实例入参
 * @date 2022/09/14 14:04:15
 */
public class NET_IN_MSGROUP_CLOSE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 主从跟踪组句柄
	 */
    public int              dwToken;

	public NET_IN_MSGROUP_CLOSE_INFO() {
		this.dwSize = this.size();
	}
}

