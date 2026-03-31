package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_AccessControlCaptureNewCard 输出结构体
 * @date 2022/12/30 10:55:26
 */
public class NET_OUT_ACCESSCONTROL_CAPTURE_NEWCARD extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;

	public NET_OUT_ACCESSCONTROL_CAPTURE_NEWCARD() {
		this.dwSize = this.size();
	}
}

