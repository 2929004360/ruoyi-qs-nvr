package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_GetSplitWindowRect接口输入参数(获取窗口位置)
 * @date 2023/06/13 14:09:42
 */
public class DH_IN_SPLIT_GET_RECT extends NetSDKLib.SdkStructure {
    public int              dwSize;
	/**
	 * 通道号(屏号)
	 */
    public int              nChannel;
	/**
	 * 窗口序号
	 */
    public int              nWindowID;

	public DH_IN_SPLIT_GET_RECT() {
		this.dwSize = this.size();
	}
}

