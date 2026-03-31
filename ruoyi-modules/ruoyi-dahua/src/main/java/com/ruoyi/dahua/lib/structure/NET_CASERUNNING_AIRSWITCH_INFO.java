package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 智慧空开状态信息
 * @date 2023/05/11 16:03:12
 */
public class NET_CASERUNNING_AIRSWITCH_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 电压，单位：v
	 */
    public byte[]           szVol = new byte[32];
	/**
	 * 电流，单位：a
	 */
    public byte[]           szECurrent = new byte[32];
	/**
	 * 功率，单位：w
	 */
    public byte[]           szPower = new byte[32];
	/**
	 * 在位检测状态，01 在位状态 ，00 不在位状态
	 */
    public byte[]           szOnPosition = new byte[32];
	/**
	 * 开关状态，01 打开状态，00 关闭状态
	 */
    public byte[]           szState = new byte[32];
	/**
	 * 版本
	 */
    public byte[]           szVersion = new byte[32];
	/**
	 * mac地址
	 */
    public byte[]           szMac = new byte[32];
	/**
	 * 序列号
	 */
    public byte[]           szSn = new byte[32];
	/**
	 * 编号
	 */
    public byte[]           szId = new byte[32];
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[256];

	public NET_CASERUNNING_AIRSWITCH_INFO() {
	}
}

