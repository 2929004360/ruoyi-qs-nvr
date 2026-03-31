package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 窗口层叠次序
 * @date 2023/06/13 14:14:47
 */
public class DH_WND_ZORDER extends NetSDKLib.SdkStructure {
    public int              dwSize;
	/**
	 * 窗口序号
	 */
    public int              nWindowID;
	/**
	 * Z次序
	 */
    public int              nZOrder;

	public DH_WND_ZORDER() {
		this.dwSize = this.size();
	}
}

