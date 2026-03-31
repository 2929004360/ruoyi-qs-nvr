package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 违章关联的多个视频信息
 * @date 2022/06/16 10:26:31
 */
public class NET_RELATING_VIDEO_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 违章关联视频FTP上传路径
	 */
    public byte[]           szVideoPath = new byte[256];
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[128];

	public NET_RELATING_VIDEO_INFO() {
	}
}

