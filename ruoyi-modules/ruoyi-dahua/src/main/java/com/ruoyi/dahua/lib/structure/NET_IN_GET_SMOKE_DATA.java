package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_GetSmokeData 入参
 * @date 2022/10/17 13:45:09
 */
public class NET_IN_GET_SMOKE_DATA extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 通道号
	 */
    public int              nChannel;

	public NET_IN_GET_SMOKE_DATA() {
		this.dwSize = this.size();
	}
}

