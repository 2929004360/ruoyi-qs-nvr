package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_FaceRServerGetDetectToken 接口输入参数
 * @date 2023/04/20 14:04:21
 */
public class NET_IN_FACERSERVER_GETDETEVTTOKEN extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;

	public NET_IN_FACERSERVER_GETDETEVTTOKEN() {
		this.dwSize = this.size();
	}
}

