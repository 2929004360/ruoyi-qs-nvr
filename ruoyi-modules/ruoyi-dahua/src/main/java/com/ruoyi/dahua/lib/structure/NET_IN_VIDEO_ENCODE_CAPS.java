package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 获取视频编码能力集入参
 * @date 2022/07/20 13:52:07
 */
public class NET_IN_VIDEO_ENCODE_CAPS extends NetSDKLib.SdkStructure {
	/**
	 * 该结构体大小
	 */
    public int              dwSize;
	/**
	 * 通道号
	 */
    public int              nChannel;
	/**
	 * 组号
	 */
    public int              nGroup;
	/**
	 * 码流类型,参考枚举{ @link CFG_EM_STREAM_TYPES } 
	 */
    public int              stStreamType;

	public NET_IN_VIDEO_ENCODE_CAPS() {
		this.dwSize = this.size();
	}
}

