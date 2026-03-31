package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 表示硬盘的基本信息
 * @date 2022/05/16 11:16:17
 */
public class DHDEV_SMART_VALUE extends NetSDKLib.SdkStructure {
	/**
	 * ID
	 */
    public byte             byId;
	/**
	 * 属性值
	 */
    public byte             byCurrent;
	/**
	 * 最大出错值
	 */
    public byte             byWorst;
	/**
	 * 阈值
	 */
    public byte             byThreshold;
	/**
	 * 属性名
	 */
    public byte[]           szName = new byte[64];
	/**
	 * 实际值
	 */
    public byte[]           szRaw = new byte[8];
	/**
	 * 状态
	 */
    public int              nPredict;
    public byte[]           reserved = new byte[128];

	public DHDEV_SMART_VALUE() {
	}
}

