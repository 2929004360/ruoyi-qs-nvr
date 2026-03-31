package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @description 平台下发轮询配置入参
 * @date 2022/07/04 10:56:48
 */
public class NET_IN_SET_POLLING_CONFIG extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 平台下发轮询配置个数
	 */
    public int              nConfigCnt;
	/**
	 * 平台下发轮询配置信息
	 */
    public NET_SET_POLLING_CONFIG_INFO[] stuConfigInfos = new NET_SET_POLLING_CONFIG_INFO[256];

	public NET_IN_SET_POLLING_CONFIG() {
		for (int i = 0; i < stuConfigInfos.length; i++) {
			stuConfigInfos[i] = new NET_SET_POLLING_CONFIG_INFO();
		}
		this.dwSize = this.size();
	}
}

