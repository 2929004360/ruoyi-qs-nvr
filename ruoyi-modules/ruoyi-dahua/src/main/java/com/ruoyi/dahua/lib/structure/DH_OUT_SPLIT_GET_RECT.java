package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_GetSplitWindowRect接口输出参数(获取窗口位置)
 * @date 2023/06/13 14:09:53
 */
public class DH_OUT_SPLIT_GET_RECT extends NetSDKLib.SdkStructure {
    public int              dwSize;
	/**
	 * 窗口位置, 0~8191
	 */
    public NetSDKLib.DH_RECT stuRect = new NetSDKLib.DH_RECT();

	public DH_OUT_SPLIT_GET_RECT() {
		this.dwSize = this.size();
	}
}

