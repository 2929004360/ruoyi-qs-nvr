package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 订阅大图检测小图进度回调结构体
 * @date 2023/04/20 15:11:18
 */
public class NET_CB_MULTIFACE_DETECT_STATE extends NetSDKLib.SdkStructure {
	/**
	 * 检测进度
	 */
    public int              nProgress;
	/**
	 * 大图检测小图的检测结果
	 */
    public NET_IMAGE_RELATION stuImageRelation = new NET_IMAGE_RELATION();
	/**
	 * 保留字节
	 */
    public byte[]           byReserved = new byte[512];

	public NET_CB_MULTIFACE_DETECT_STATE() {
	}
}

