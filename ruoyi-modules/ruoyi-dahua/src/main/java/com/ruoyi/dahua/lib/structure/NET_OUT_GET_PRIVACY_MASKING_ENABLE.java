package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_GetPrivacyMaskingEnable 输出参数
 * @date 2022/07/21 17:15:41
 */
public class NET_OUT_GET_PRIVACY_MASKING_ENABLE extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * true 开 false 关
	 */
    public int              bEnable;

	public NET_OUT_GET_PRIVACY_MASKING_ENABLE() {
		this.dwSize = this.size();
	}
}

