package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 屏蔽倍率能力集
 * @date 2022/07/21 13:40:56
 */
public class NET_SHIELD_ZOOM_CAPS extends NetSDKLib.SdkStructure {
	/**
	 * 是否支持设置屏蔽倍率，true-支持, false-不支持
	 */
    public int              bSupport;
	/**
	 * 屏蔽倍率最小值若无该字段则最小值为0。真实倍率扩大10倍
	 */
    public int              nShieldZoomMin;
	/**
	 * 屏蔽倍率最大值，若无该字段则最大值不做限制。真实倍率扩大10倍
	 */
    public int              nShieldZoomMax;
	/**
	 * 保留字节
	 */
    public byte[]           byReserved = new byte[128];

	public NET_SHIELD_ZOOM_CAPS() {
	}
}

