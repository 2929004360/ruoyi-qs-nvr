package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_SetPrivacyMasking 输入参数
 * @date 2022/07/21 17:11:44
 */
public class NET_IN_SET_PRIVACY_MASKING extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 通道号
	 */
    public int              nChannel;
	/**
	 * 隐私遮挡块信息
	 */
    public NET_PRIVACY_MASKING_INFO stuPrivacyMaskingInfo = new NET_PRIVACY_MASKING_INFO();

	public NET_IN_SET_PRIVACY_MASKING() {
		this.dwSize = this.size();
	}
}

