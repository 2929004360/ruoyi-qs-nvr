package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description CLIENT_GetBindMode 接口出参
 * @origin autoTool
 * @date 2023/09/15 10:59:48
 */
public class NET_OUT_GET_BIND_MODE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * / 此结构体大小,必须赋值
	 */
    public int              dwSize;
	/**
	 * / 绑定模式 {@link com.ruoyi.dahua.lib.enumeration.EM_BIND_MODE}
	 */
    public int              emBindMode;

	public NET_OUT_GET_BIND_MODE_INFO() {
		this.dwSize = this.size();
	}
}

