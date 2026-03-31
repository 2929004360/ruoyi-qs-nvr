package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_ModifyAlarmUser 接口输出参数
 * @date 2023/03/16 10:58:19
 */
public class NET_OUT_MODIFY_ALARM_USER extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;

	public NET_OUT_MODIFY_ALARM_USER() {
		this.dwSize = this.size();
	}
}

