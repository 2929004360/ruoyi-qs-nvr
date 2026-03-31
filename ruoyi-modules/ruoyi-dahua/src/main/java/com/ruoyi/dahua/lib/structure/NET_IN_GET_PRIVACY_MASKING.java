package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_GetPrivacyMasking 输入参数
 * @date 2022/07/21 16:58:24
 */
public class NET_IN_GET_PRIVACY_MASKING extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 通道号
	 */
    public int              nChannel;
	/**
	 * 本次从第几个遮档块开始获取
	 */
    public int              nOffset;
	/**
	 * 本次获取多少个遮档块信息,最大不超过24
	 */
    public int              nLimit;

	public NET_IN_GET_PRIVACY_MASKING() {
		this.dwSize = this.size();
	}
}

