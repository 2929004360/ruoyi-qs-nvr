package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 合规库布控信息(视频通道角度)
 * @date 2022/10/08 17:05:54
 */
public class NET_WORKSUIT_CHANNEL_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 当前组绑定的通道信息, 只读, 获取时有效
	 */
    public int              nChannel;
	/**
	 * 相似度阈值，范围 [1, 100]，默认67, 只读, 获取时有效
	 */
    public int              nSimilarity;
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[128];

	public NET_WORKSUIT_CHANNEL_INFO() {
	}
}

