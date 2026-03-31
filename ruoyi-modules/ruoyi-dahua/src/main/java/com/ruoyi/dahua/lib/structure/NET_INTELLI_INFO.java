package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @description 视频通道上的智能信息
 * @date 2022/07/04 14:04:51
 */
public class NET_INTELLI_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 智能规则类型个数
	 */
    public int              nTypeNum;
	/**
	 * 智能规则类型
	 */
    public Byte64Arr[]      szType = new Byte64Arr[16];
	/**
	 * 智能场景类型
	 */
    public byte[]           szClass = new byte[64];
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[516];

	public NET_INTELLI_INFO() {
		for (int i = 0; i < szType.length; i++) {
			szType[i] = new Byte64Arr();
		}

	}
}

