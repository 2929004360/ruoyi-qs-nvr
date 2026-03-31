package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_QueryDevInfo NET_QUERY_PTZ_CURRENT_FOV_VALUE 类型接口输入参数
 * @date 2022/09/22 15:28:44
 */
public class NET_IN_PTZ_CURRENT_FOV_VALUE extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 通道号
	 */
    public int              nChannel;

	public NET_IN_PTZ_CURRENT_FOV_VALUE() {
		this.dwSize = this.size();
	}
}

