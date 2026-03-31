package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description CLIENT_SetBindMode 接口出参
 * @origin autoTool
 * @date 2023/09/15 10:59:47
 */
public class NET_OUT_SET_BIND_MODE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * / 此结构体大小,必须赋值
	 */
    public int              dwSize;

	public NET_OUT_SET_BIND_MODE_INFO() {
		this.dwSize = this.size();
	}
}

