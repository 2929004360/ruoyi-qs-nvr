package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @version 1.0
 * @description 图片与智能事件信息
 * @date 2021/12/28
 */
public class NET_FILE_PROCESS_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 图片信息数组
	 */
    public NET_IMAGE_INFO_EX2[] stuImageInfo = (NET_IMAGE_INFO_EX2[]) new NET_IMAGE_INFO_EX2().toArray(32);
	/**
	 * 图片信息个数
	 */
    public int              nImageInfoNum;
	/**
	 * 违章关联的多个视频信息个数
	 */
    public int              nRelatingVideoInfoNum;
	/**
	 * 违章关联的多个视频信息数组，最多支持16个视频
	 */
    public NET_RELATING_VIDEO_INFO[] stuRelatingVideoInfo = new NET_RELATING_VIDEO_INFO[16];
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[1024];

	public NET_FILE_PROCESS_INFO() {
		for (int i = 0; i < stuImageInfo.length; i++) {
			stuImageInfo[i] = new NET_IMAGE_INFO_EX2();
		}
		for (int i = 0; i < stuRelatingVideoInfo.length; i++) {
			stuRelatingVideoInfo[i] = new NET_RELATING_VIDEO_INFO();
		}
	}
}

