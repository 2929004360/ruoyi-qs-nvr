package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @description CLIENT_GetChannelState 入参
 * @date 2022/07/04 11:16:31
 */
public class NET_IN_GET_CHANNEL_STATE extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 视频通道数组个数
	 */
    public int              nChannelNum;
	/**
	 * 视频通道数组，当该字段不存在或为空时代表获取所有通道
	 */
    public int[]            nChannel = new int[256];

	public NET_IN_GET_CHANNEL_STATE() {
		this.dwSize = this.size();
	}
}

