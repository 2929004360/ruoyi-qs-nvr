package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description OSD信息
 * @date 2022/06/17 11:14:35
 */
public class NET_DVR_VIDEOITEM extends NetSDKLib.SdkStructure {
	/**
	 * OSD叠加的位置和背景色
	 */
    public NET_DVR_VIDEO_WIDGET StOSD_POS = new NET_DVR_VIDEO_WIDGET();
	/**
	 * OSD叠加的名称
	 */
    public byte[]           SzOSD_Name = new byte[64];

	public NET_DVR_VIDEOITEM() {
	}
}

