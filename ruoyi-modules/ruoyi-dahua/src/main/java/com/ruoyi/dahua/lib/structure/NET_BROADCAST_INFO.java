package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib.SdkStructure;

public class NET_BROADCAST_INFO extends SdkStructure {
	/**
	 * 语音文本
	 */
    public byte[]           szText = new byte[256];
	/**
	 * 文本类型EM_BROADCAST_TEXT_TYPE
	 */
    public int              emTextType;
	/**
	 * 保留字节
	 */
    public byte[]           byReserved = new byte[252];
}

