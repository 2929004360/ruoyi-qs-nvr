package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_SetSplitTopWindow接口输入参数(设置窗口次序)
 * @date 2023/06/13 14:12:53
 */
public class DH_IN_SPLIT_SET_TOP_WINDOW extends NetSDKLib.SdkStructure {
    public int              dwSize;
	/**
	 * 通道号(屏号)
	 */
    public int              nChannel;
	/**
	 * 窗口序号
	 */
    public int              nWindowID;

	public DH_IN_SPLIT_SET_TOP_WINDOW() {
		this.dwSize = this.size();
	}
}

