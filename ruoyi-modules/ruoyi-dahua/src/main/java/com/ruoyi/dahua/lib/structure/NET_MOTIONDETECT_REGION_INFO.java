package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 动检区域信息
 * @date 2023/05/15 13:47:59
 */
public class NET_MOTIONDETECT_REGION_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 区域ID
	 */
    public int              nRegionID;
	/**
	 * 区域名称
	 */
    public byte[]           szRegionName = new byte[64];
	/**
	 * 保留字节
	 */
    public byte[]           bReserved = new byte[508];

	public NET_MOTIONDETECT_REGION_INFO() {
	}
}

