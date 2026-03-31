package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_AddAlarmUser 接口输入参数
 * @date 2023/03/16 09:04:51
 */
public class NET_IN_ADD_ALARM_USER extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 添加的用户信息
	 */
    public NET_ALARM_USER   stuAlarmUser = new NET_ALARM_USER();

	public NET_IN_ADD_ALARM_USER() {
		this.dwSize = this.size();
	}
}

