package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 设置旁路状态
 *              输出参数，此时CLIENT_SetAlarmRegionInfo的emType参数为NET_EM_SET_ALARMREGION_INFO_BYPASSMODE
 * @date 2023/03/15 16:34:13
 */
public class NET_OUT_SET_BYPASSMODE extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;

	public NET_OUT_SET_BYPASSMODE() {
		this.dwSize = this.size();
	}
}

