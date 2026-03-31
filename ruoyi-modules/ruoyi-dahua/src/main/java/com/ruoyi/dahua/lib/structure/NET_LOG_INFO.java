package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 日志信息
 * @date 2023/06/12 09:24:31
 */
public class NET_LOG_INFO extends NetSDKLib.SdkStructure {
    public int              dwSize;
	/**
	 * 时间
	 */
    public NET_TIME         stuTime = new NET_TIME();
	/**
	 * 操作者
	 */
    public byte[]           szUserName = new byte[32];
	/**
	 * 类型
	 */
    public byte[]           szLogType = new byte[128];
	/**
	 * 日志信息
	 */
    public NET_LOG_MESSAGE  stuLogMsg = new NET_LOG_MESSAGE();
	/**
	 * 记录UTC时间
	 */
    public NET_TIME         stuTimeRealUTC = new NET_TIME();

	public NET_LOG_INFO() {
		this.dwSize = this.size();
	}
}

