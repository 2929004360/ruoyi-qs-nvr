package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 动环设备
 * @date 2023/05/11 14:09:04
 */
public class NET_LAMP_EXTERNAL_DEVICE_ENVIRONMENT_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 动环设备ID
	 */
    public byte[]           szId = new byte[32];
	/**
	 * 湿度
	 */
    public byte[]           szHumidity = new byte[32];
	/**
	 * 气压
	 */
    public byte[]           szPressure = new byte[32];
	/**
	 * 风速
	 */
    public byte[]           szWindSpeed = new byte[32];
	/**
	 * 风向
	 */
    public byte[]           szWindDirection = new byte[32];
	/**
	 * 温度
	 */
    public byte[]           szTemperature = new byte[32];
	/**
	 * 分贝
	 */
    public byte[]           szDecibel = new byte[32];
	/**
	 * PM2.5
	 */
    public byte[]           szPM2_5 = new byte[32];
	/**
	 * PM10
	 */
    public byte[]           szPM10 = new byte[32];
	/**
	 * 雨量
	 */
    public byte[]           szRainfall = new byte[32];
	/**
	 * 辐射
	 */
    public byte[]           szRadiation = new byte[32];
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[256];

	public NET_LAMP_EXTERNAL_DEVICE_ENVIRONMENT_INFO() {
	}
}

