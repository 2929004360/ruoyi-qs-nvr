package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_AttachLowRateWPAN()输出参数
 * @date 2023/03/16 16:16:48
 */
public class NET_OUT_ATTACH_LOWRATEWPAN extends NetSDKLib.SdkStructure {
    public int              dwSize;

	public NET_OUT_ATTACH_LOWRATEWPAN() {
		this.dwSize = this.size();
	}
}

