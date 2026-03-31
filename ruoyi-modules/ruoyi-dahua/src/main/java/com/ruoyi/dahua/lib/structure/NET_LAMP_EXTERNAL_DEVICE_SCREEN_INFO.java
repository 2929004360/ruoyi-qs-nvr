package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description LED广告屏
 * @date 2023/05/11 14:09:04
 */
public class NET_LAMP_EXTERNAL_DEVICE_SCREEN_INFO extends NetSDKLib.SdkStructure {
	/**
	 * LED广告屏IP地址
	 */
    public byte[]           szIp = new byte[32];
	/**
	 * LED广告屏序列号
	 */
    public byte[]           szSN = new byte[32];
	/**
	 * LED广告屏与终端的连接状态
	 */
    public int              bConnectedState;
	/**
	 * LED广告屏链接平台的连接状态
	 */
    public int              bPlatformConnectedState;
	/**
	 * LED广告屏控制开关
	 */
    public int              bOnOffState;
	/**
	 * LED广告屏亮度控制
	 */
    public int              nBrightness;
	/**
	 * LED广告屏音量控制
	 */
    public int              nVolume;
	/**
	 * 显示状态数量
	 */
    public int              nDisplayEnvironmentNum;
	/**
	 * 0~9个元素分别表示气象要素的显示状态：湿度、气压、风速、风向、温度、分贝、PM2.5、PM10、雨量和辐射的叠加显示状态
	 */
    public int[]            bDisplayEnvironment = new int[10];
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[256];

	public NET_LAMP_EXTERNAL_DEVICE_SCREEN_INFO() {
	}
}

