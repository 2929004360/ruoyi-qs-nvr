package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 表示硬盘的基本信息
 * @date 2022/05/16 11:16:17
 */
public class DHDEV_DEVICE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 型号
	 */
    public byte[]           byModle = new byte[32];
	/**
	 * 序列号
	 */
    public byte[]           bySerialNumber = new byte[32];
	/**
	 * 固件号
	 */
    public byte[]           byFirmWare = new byte[32];
	/**
	 * ATA协议版本号
	 */
    public int              nAtaVersion;
	/**
	 * smart 信息数
	 */
    public int              nSmartNum;
    public long             Sectors;
	/**
	 * 磁盘状态 0-正常 1-异常
	 */
    public int              nStatus;
	/**
	 * 保留字节
	 */
    public int[]            nReserved = new int[33];

	public DHDEV_DEVICE_INFO() {
	}
}

