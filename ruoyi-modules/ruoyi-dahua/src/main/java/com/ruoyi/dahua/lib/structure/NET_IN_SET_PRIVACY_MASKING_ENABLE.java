package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_SetPrivacyMaskingEnable 输入参数
 * @date 2022/07/21 17:13:49
 */
public class NET_IN_SET_PRIVACY_MASKING_ENABLE extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 通道号
	 */
    public int              nChannel;
	/**
	 * true 使能所有隐私遮挡块 false 关闭所有隐私遮挡块
	 */
    public int              bEnable;

	public NET_IN_SET_PRIVACY_MASKING_ENABLE() {
		this.dwSize = this.size();
	}
}

