package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 照明灯设备
 * @date 2023/05/11 14:09:04
 */
public class NET_LAMP_EXTERNAL_DEVICE_LIGHT_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 灯的地址
	 */
    public byte[]           szId = new byte[32];
	/**
	 * 灯的分组
	 */
    public byte[]           szGroup = new byte[32];
	/**
	 * 灯电压
	 */
    public byte[]           szVoltage = new byte[32];
	/**
	 * 灯电流
	 */
    public byte[]           szCurrent = new byte[32];
	/**
	 * 灯版本信息
	 */
    public byte[]           szVersion = new byte[32];
	/**
	 * 灯开关控制
	 */
    public int              bOnOffState;
	/**
	 * 灯亮度控制
	 */
    public int              nBrightness;
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[256];

	public NET_LAMP_EXTERNAL_DEVICE_LIGHT_INFO() {
	}
}

