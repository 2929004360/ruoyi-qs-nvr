package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 每个通道的OSD信息
 * @date 2022/06/17 11:09:14
 */
public class NET_DVR_VIDEOOSD_CFG extends NetSDKLib.SdkStructure {
    public int              dwSize;
	/**
	 * 每个通道的OSD信息
	 */
    public NET_DVR_VIDEOITEM[] StOSDTitleOpt = new NET_DVR_VIDEOITEM[8];
	/**
	 * 保留
	 */
    public byte[]           bReserved = new byte[16];

	public NET_DVR_VIDEOOSD_CFG() {
		for (int i = 0; i < StOSDTitleOpt.length; i++) {
			StOSDTitleOpt[i] = new NET_DVR_VIDEOITEM();
		}
		this.dwSize = this.size();
	}
}

