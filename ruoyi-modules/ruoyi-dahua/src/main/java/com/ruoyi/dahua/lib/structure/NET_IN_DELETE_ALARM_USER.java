package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_DeleteAlarmUser 接口输入参数
 * @date 2023/03/16 11:04:41
 */
public class NET_IN_DELETE_ALARM_USER extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 用户ID，用户唯一标识
	 */
    public byte[]           szUserID = new byte[64];

	public NET_IN_DELETE_ALARM_USER() {
		this.dwSize = this.size();
	}
}

