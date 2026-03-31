package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @description 用户信息图片信息
 * @origin autoTool
 * @date 2023/07/28 14:34:03
 */
public class NET_USERMANAGER_IMAGE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 在二进制数据块中的偏移,单位:字节
	 */
    public int              nOffset;
	/**
	 * 图片大小,单位:字节
	 */
    public int              nLength;
	/**
	 * 图片宽度
	 */
    public short            nWidth;
	/**
	 * 图片高度
	 */
    public short            nHeight;
	/**
	 * 图片类型 {@link com.ruoyi.dahua.lib.enumeration.EM_USERMANAGER_IMAGE_TYPE}
	 */
    public int              emImageType;
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[64];

	public NET_USERMANAGER_IMAGE_INFO() {
	}
}

