package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 关闭的智能规则信息
 * @date 2022/10/31 19:28:33
 */
public class NET_CHANNEL_STATE_INFO_EX extends NetSDKLib.SdkStructure {
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
	 * 已关闭的智能规则信息个数
	 */
    public int              nClosedIntelliInfoNum;
	/**
	 * 已关闭的智能规则信息
	 */
    public NET_INTELLI_INFO[] stuClosedIntelliInfo = new NET_INTELLI_INFO[64];
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[512];

	public NET_CHANNEL_STATE_INFO_EX() {
		for (int i = 0; i < stuIntelliInfo.length; i++) {
			stuIntelliInfo[i] = new NET_INTELLI_INFO();
		}
		for (int i = 0; i < stuClosedIntelliInfo.length; i++) {
			stuClosedIntelliInfo[i] = new NET_INTELLI_INFO();
		}
	}
}

