package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 目标检测框颜色
 * @origin autoTool
 * @date 2023/07/31 09:25:34
 */
public class NET_FACEBOXCOLOR_CAPS extends NetSDKLib.SdkStructure {
	/**
	 * 是否支持目标检测框颜色上报
	 */
    public int              bSupport;
	/**
	 * 默认颜色
	 */
    public NET_COLOR_RGBA   stuDefaultColor = new NET_COLOR_RGBA();
	/**
	 * 保留
	 */
    public byte[]           byReserved = new byte[1024];

	public NET_FACEBOXCOLOR_CAPS() {
	}
}

