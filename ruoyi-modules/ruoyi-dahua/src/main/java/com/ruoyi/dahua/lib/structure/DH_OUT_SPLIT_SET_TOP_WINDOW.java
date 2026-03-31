package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @description CLIENT_SetSplitTopWindow接口输出参数(设置窗口次序)
 * @date 2023/06/13 14:13:08
 */
public class DH_OUT_SPLIT_SET_TOP_WINDOW extends NetSDKLib.SdkStructure {
    public int              dwSize;
	/**
	 * 窗口次序数组,由用户申请内存,大小为sizeof(DH_WND_ZORDER)*nMaxWndCount
	 */
    public Pointer          pZOders;
	/**
	 * 窗口次序数组大小
	 */
    public int              nMaxWndCount;
	/**
	 * 返回的窗口数量
	 */
    public int              nWndCount;

	public DH_OUT_SPLIT_SET_TOP_WINDOW() {
		this.dwSize = this.size();
	}
}

