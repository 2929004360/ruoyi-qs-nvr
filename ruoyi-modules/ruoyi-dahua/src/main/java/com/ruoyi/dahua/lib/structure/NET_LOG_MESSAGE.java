package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 日志详细信息
 * @date 2023/06/12 09:24:31
 */
public class NET_LOG_MESSAGE extends NetSDKLib.SdkStructure {
    public int              dwSize;
	/**
	 * 日志内容
	 */
    public byte[]           szLogMessage = new byte[1024];

	public NET_LOG_MESSAGE() {
		this.dwSize = this.size();
	}
}

