package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 小图信息
 * @date 2023/04/20 15:18:00
 */
public class NET_IMAGE_RELATION_EX_IMAGEINFO extends NetSDKLib.SdkStructure {
	/**
	 * 在二进制数据块中的偏移
	 */
    public int              nOffset;
	/**
	 * 图片大小,单位字节
	 */
    public int              nLength;
	/**
	 * 预留字节
	 */
    public byte[]           szReserved = new byte[16];

	public NET_IMAGE_RELATION_EX_IMAGEINFO() {
	}
}

