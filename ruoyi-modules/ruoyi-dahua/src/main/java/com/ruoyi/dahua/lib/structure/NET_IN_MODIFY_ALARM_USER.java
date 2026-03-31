package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_ModifyAlarmUser 接口输入参数
 * @date 2023/03/16 10:58:09
 */
public class NET_IN_MODIFY_ALARM_USER extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 用户ID，用户唯一标识
	 */
    public byte[]           szUserID = new byte[64];
	/**
	 * 修改的用户信息
	 */
    public NET_ALARM_USER   stuAlarmUser = new NET_ALARM_USER();

	public NET_IN_MODIFY_ALARM_USER() {
		this.dwSize = this.size();
	}
}

