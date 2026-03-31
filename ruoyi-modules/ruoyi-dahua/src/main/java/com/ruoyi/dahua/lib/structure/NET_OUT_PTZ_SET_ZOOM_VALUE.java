package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description CLIENT_PTZSetZoomValue 接口输出参数
 * @origin autoTool
 * @date 2023/10/16 14:11:22
 */
public class NET_OUT_PTZ_SET_ZOOM_VALUE extends NetSDKLib.SdkStructure {
	/**
	 *  结构体大小
	 */
    public int              dwSize;

	public NET_OUT_PTZ_SET_ZOOM_VALUE() {
		this.dwSize = this.size();
	}
}

