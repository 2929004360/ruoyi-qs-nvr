package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 表示硬盘的基本信息
 * @date 2022/05/16 11:16:17
 */
public class DHDEV_SMART_HARDDISK extends NetSDKLib.SdkStructure {
	/**
	 * 硬盘号
	 */
    public byte             nDiskNum;
	/**
	 * Raid子盘,0表示单盘
	 */
    public byte             byRaidNO;
	/**
	 * 保留字节
	 */
    public byte[]           byReserved = new byte[2];
	/**
	 * 表示硬盘的基本信息
	 */
    public DHDEV_DEVICE_INFO deviceInfo = new DHDEV_DEVICE_INFO();
	/**
	 * 硬盘的smart信息,可能会有很多条,最多不超过30个
	 */
    public DHDEV_SMART_VALUE[] smartValue = new DHDEV_SMART_VALUE[NetSDKLib.MAX_SMART_VALUE_NUM];

	public DHDEV_SMART_HARDDISK() {
		for (int i = 0; i < smartValue.length; i++) {
			smartValue[i] = new DHDEV_SMART_VALUE();
		}
	}
}

