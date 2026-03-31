package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @version 1.0
 * @description 设置停车规则接口，CLIENT_SetParkingRule 出参
 * @date 2021/05/28
 */
public class NET_OUT_SET_PARKINGRULE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;

	public NET_OUT_SET_PARKINGRULE_INFO() {
		this.dwSize = this.size();
	}
}

