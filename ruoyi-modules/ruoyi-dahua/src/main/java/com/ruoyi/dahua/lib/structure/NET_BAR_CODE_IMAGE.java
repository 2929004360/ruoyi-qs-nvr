package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 条码可见光图
 * @date 2022/10/31 09:30:19
 */
public class NET_BAR_CODE_IMAGE extends NetSDKLib.SdkStructure {
	/**
	 * 在二进制数据块中的偏移
	 */
    public int              nOffset;
	/**
	 * 图片大小,单位：字节
	 */
    public int              nLength;
	/**
	 * 保留字节
	 */
    public byte[]           szResvered = new byte[248];

	public NET_BAR_CODE_IMAGE() {
	}
}

