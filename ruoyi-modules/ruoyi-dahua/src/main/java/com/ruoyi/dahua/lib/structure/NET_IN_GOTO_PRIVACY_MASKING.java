package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_GotoPrivacyMasking 输入参数
 * @date 2022/07/21 17:19:47
 */
public class NET_IN_GOTO_PRIVACY_MASKING extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 通道号
	 */
    public int              nChannel;
	/**
	 * 遮档块编号，从0开始
	 */
    public int              nIndex;

	public NET_IN_GOTO_PRIVACY_MASKING() {
		this.dwSize = this.size();
	}
}

