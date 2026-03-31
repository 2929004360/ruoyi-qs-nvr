package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_ModifyAlarmUserPassword 接口输出参数
 * @date 2023/03/16 11:01:56
 */
public class NET_OUT_MODIFY_ALARM_USER_PASSWORD extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;

	public NET_OUT_MODIFY_ALARM_USER_PASSWORD() {
		this.dwSize = this.size();
	}
}

