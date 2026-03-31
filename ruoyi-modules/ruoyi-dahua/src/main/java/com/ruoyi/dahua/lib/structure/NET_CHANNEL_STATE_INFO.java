package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @description 智能业务状态信息
 * @date 2022/07/04 14:04:51
 */
public class NET_CHANNEL_STATE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 通道号
	 */
    public int              nChannel;
	/**
	 * 已开启的智能规则信息个数
	 */
    public int              nIntelliInfoNum;
	/**
	 * 已开启的智能规则信息
	 */
    public NET_INTELLI_INFO[] stuIntelliInfo = new NET_INTELLI_INFO[16];
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[512];

	public NET_CHANNEL_STATE_INFO() {
		for (int i = 0; i < stuIntelliInfo.length; i++) {
			stuIntelliInfo[i] = new NET_INTELLI_INFO();
		}
	}
}

