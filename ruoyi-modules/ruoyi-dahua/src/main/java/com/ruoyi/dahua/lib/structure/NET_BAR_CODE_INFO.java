package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 与包裹关联的单号的内容
 * @date 2022/10/31 09:30:19
 */
public class NET_BAR_CODE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 条码个数
	 */
    public int              nCodeCount;
	/**
	 * 条码信息
	 */
    public byte[]           szCode = new byte[32 * 128];
	/**
	 * 条码可见光图
	 */
    public NET_BAR_CODE_IMAGE stuImage = new NET_BAR_CODE_IMAGE();
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[256];

	public NET_BAR_CODE_INFO() {
	}
}

