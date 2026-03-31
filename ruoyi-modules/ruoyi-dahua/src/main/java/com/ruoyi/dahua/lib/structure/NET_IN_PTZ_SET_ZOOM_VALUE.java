package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description CLIENT_PTZSetZoomValue 接口输入参数
 * @origin autoTool
 * @date 2023/10/16 14:11:21
 */
public class NET_IN_PTZ_SET_ZOOM_VALUE extends NetSDKLib.SdkStructure {
	/**
	 *  结构体大小
	 */
    public int              dwSize;
	/**
	 *  通道号, 范围 0~设备通道数
	 */
    public int              nChannel;
	/**
	 *  变倍值，扩大10倍表示
	 */
    public int              nValue;

	public NET_IN_PTZ_SET_ZOOM_VALUE() {
		this.dwSize = this.size();
	}
}

