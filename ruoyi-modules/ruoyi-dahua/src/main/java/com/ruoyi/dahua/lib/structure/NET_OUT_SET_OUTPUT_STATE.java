package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 设置输出状态输出参数。此时CLIENT_SetAlarmRegionInfo的emType参数为NET_EM_SET_ALARMREGION_INFO_OUTPUTSTATE
 * @date 2023/03/15 16:49:41
 */
public class NET_OUT_SET_OUTPUT_STATE extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;

	public NET_OUT_SET_OUTPUT_STATE() {
		this.dwSize = this.size();
	}
}

