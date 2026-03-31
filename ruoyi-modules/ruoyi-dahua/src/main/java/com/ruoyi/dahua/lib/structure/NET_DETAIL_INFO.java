package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 执行布撤防时的附加信息
 * @date 2023/03/15 16:05:04
 */
public class NET_DETAIL_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 布撤防详细信息
	 */
    public NET_ARM_OPTIONS  stuArmOption = new NET_ARM_OPTIONS();
	/**
	 * 预留字节
	 */
    public byte[]           byReserved = new byte[128];

	public NET_DETAIL_INFO() {
	}
}

