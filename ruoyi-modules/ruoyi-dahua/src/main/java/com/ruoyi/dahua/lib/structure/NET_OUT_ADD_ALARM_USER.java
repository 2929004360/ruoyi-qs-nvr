package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_AddAlarmUser 接口输出参数
 * @date 2023/03/16 09:05:02
 */
public class NET_OUT_ADD_ALARM_USER extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;

	public NET_OUT_ADD_ALARM_USER() {
		this.dwSize = this.size();
	}
}

