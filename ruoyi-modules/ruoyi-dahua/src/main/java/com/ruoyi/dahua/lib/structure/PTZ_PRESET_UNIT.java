package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 预置点的坐标和放大倍数
 * @date 2022/05/10 20:41:58
 */
public class PTZ_PRESET_UNIT extends NetSDKLib.SdkStructure {
	/**
	 * 水平坐标，范围[0,3599]，表示0度到359.9度，度数扩大10倍表示。
	 */
    public int              nPositionX;
	/**
	 * 垂直坐标，范围[-1800,1800]，表示-180.0度到+180.0度，度数扩大10倍表示。
	 */
    public int              nPositionY;
	/**
	 * 放大参数，范围[0,127]，表示最小倍到最大倍的变倍位置
	 */
    public int              nZoom;
	/**
	 * 预留32字节
	 */
    public byte[]           szReserve = new byte[32];

	public PTZ_PRESET_UNIT() {
	}
}

