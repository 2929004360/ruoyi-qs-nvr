package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 图片扩展信息
 * @origin autoTool
 * @date 2023/06/19 14:53:51
 */
public class NET_IMAGE_INFO_EX3 extends NetSDKLib.SdkStructure {
	/**
	 * 图片类型 {@link com.ruoyi.dahua.lib.enumeration.EM_IMAGE_TYPE_EX2}
	 */
    public int              emType;
	/**
	 * 在二进制数据块中的偏移
	 */
    public int              nOffset;
	/**
	 * 图片大小,单位:字节
	 */
    public int              nLength;
	/**
	 * 加密密钥标记, 区分使用哪种长度密钥, 0: 使用 szEncryptKey, 1: 使用 szEncryptLKey
	 */
    public int              nEncryptFlag;
	/**
	 * 图片存储位置
	 */
    public byte[]           szPath = new byte[256];
	/**
	 * 二进制图片加密秘钥，设备传过来的是Base64编码之后的，SDK不做解码处理
	 */
    public byte[]           szEncryptKey = new byte[128];
	/**
	 * 二进制图片加密秘钥，设备传过来的是Base64编码之后的，SDK不做解码处理
	 */
    public byte[]           szEncryptLKey = new byte[256];
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
    public byte[]           byReserverd2 = new byte[760];

	public NET_IMAGE_INFO_EX3() {
	}
}

