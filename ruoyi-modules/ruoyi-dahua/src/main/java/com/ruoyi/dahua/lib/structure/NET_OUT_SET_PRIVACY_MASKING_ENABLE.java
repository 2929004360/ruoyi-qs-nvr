package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_SetPrivacyMaskingEnable 输出参数
 * @date 2022/07/21 17:14:02
 */
public class NET_OUT_SET_PRIVACY_MASKING_ENABLE extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;

	public NET_OUT_SET_PRIVACY_MASKING_ENABLE() {
		this.dwSize = this.size();
	}
}

