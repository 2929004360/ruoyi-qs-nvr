package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 大图中小图的信息
 * @date 2023/04/20 15:11:18
 */
public class NET_SMALL_PIC_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 小图ID
	 */
    public int              nSmallPicId;
	/**
	 * 小图在大图中的位置
	 */
    public NET_RECT         stuRect = new NET_RECT();
	/**
	 * 目标类型 {@link EM_OBJECT_TYPE}
	 */
    public int              emDetectObjType;
	/**
	 * 保留字节
	 */
    public byte[]           bReserved = new byte[124];

	public NET_SMALL_PIC_INFO() {
	}
}

