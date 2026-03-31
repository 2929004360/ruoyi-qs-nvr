package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_GetPrivacyMaskingEnable 输入参数
 * @date 2022/07/21 17:15:29
 */
public class NET_IN_GET_PRIVACY_MASKING_ENABLE extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 通道号
	 */
    public int              nChannel;

	public NET_IN_GET_PRIVACY_MASKING_ENABLE() {
		this.dwSize = this.size();
	}
}

