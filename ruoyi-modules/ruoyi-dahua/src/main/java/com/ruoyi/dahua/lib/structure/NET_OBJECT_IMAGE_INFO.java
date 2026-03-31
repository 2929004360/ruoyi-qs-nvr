package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @version 1.0
 * @description 物体截图
 * @date 2022/03/31
 */
public class NET_OBJECT_IMAGE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 在二进制数据块中的偏移
	 */
    public int              nOffSet;
	/**
	 * 图片大小,单位字节
	 */
    public int              nLength;
	/**
	 * 图片宽度(像素)
	 */
    public int              nWidth;
	/**
	 * 图片高度(像素)
	 */
    public int              nHeight;
	/**
	 * 全景图片路径
	 */
    public byte[]           szFilePath = new byte[260];
	/**
	 * 图片序号
	 */
    public int              nIndexInData;
	/**
	 * 预留字节
	 */
    public byte[]           byReserved = new byte[504];
}

