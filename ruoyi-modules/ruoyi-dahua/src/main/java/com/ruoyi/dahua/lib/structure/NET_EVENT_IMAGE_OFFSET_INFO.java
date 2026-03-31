package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 事件图片偏移信息
 * @date 2022/12/19 11:30:58
 */
public class NET_EVENT_IMAGE_OFFSET_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 偏移
	 */
    public int              nOffSet;
	/**
	 * 图片大小,单位字节
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
	 * 图片路径
	 */
    public byte[]           szPath = new byte[260];
	/**
	 * 在上传图片数据中的图片序号
	 */
    public int              nIndexInData;
	/**
	 * 预留字节
	 */
    public byte[]           byReserved = new byte[248];

	public NET_EVENT_IMAGE_OFFSET_INFO() {
	}
}

