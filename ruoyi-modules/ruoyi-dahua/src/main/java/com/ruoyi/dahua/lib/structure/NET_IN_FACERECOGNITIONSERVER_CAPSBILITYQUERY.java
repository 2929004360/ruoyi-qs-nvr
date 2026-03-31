package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description CLIENT_GetDevCaps 对应的类型(NET_FACERECOGNITIONSE_CAPS) 输入参数
 * @origin autoTool
 * @date 2023/07/31 09:25:32
 */
public class NET_IN_FACERECOGNITIONSERVER_CAPSBILITYQUERY extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;

	public NET_IN_FACERECOGNITIONSERVER_CAPSBILITYQUERY() {
		this.dwSize = this.size();
	}
}

