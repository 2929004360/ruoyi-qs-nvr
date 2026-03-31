package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_ModifyAlarmUserPassword 接口输入参数
 * @date 2023/03/16 11:01:47
 */
public class NET_IN_MODIFY_ALARM_USER_PASSWORD extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 用户ID，用户唯一标识
	 */
    public byte[]           szUserID = new byte[64];
	/**
	 * 旧密码
	 */
    public byte[]           szOldPassword = new byte[64];
	/**
	 * 新密码
	 */
    public byte[]           szNewPassword = new byte[64];

	public NET_IN_MODIFY_ALARM_USER_PASSWORD() {
		this.dwSize = this.size();
	}
}

