package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 图片信息(目前仅用于机动车和非机动车),对应C++头文件NET_PIC_INFO_EX
 * @date 2022/12/03 13:43:56
 */
public class SDK_PIC_INFO_EX extends NetSDKLib.SdkStructure {
	/**
	 * 图片大小,单位:字节
	 */
    public int              nLength;
	/**
	 * 图片宽度
	 */
    public int              nWidth;
	/**
	 * 图片高度
	 */
    public int              nHeight;
	/**
	 * 用于字节对齐
	 */
    public byte[]           byReserverd = new byte[4];
	/**
	 * 图片路径
	 */
    public byte[]           szFilePath = new byte[256];
	/**
	 * 保留字节
	 */
    public byte[]           szReserverd = new byte[256];

	public SDK_PIC_INFO_EX() {
	}
}

