package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 输出电压状态
 * @date 2023/05/11 16:03:11
 */
public class NET_CASERUNNING_OUTV_INFO extends NetSDKLib.SdkStructure {
	/**
	 * DC12v1电压值，具体值，单位：v
	 */
    public byte[]           szDc12v1 = new byte[32];
	/**
	 * DC12v2电压值，具体值，单位:w
	 */
    public byte[]           szDc12v2 = new byte[32];
	/**
	 * DC12v1电压开关，开关状态，00关01开
	 */
    public byte[]           szDc12v1State = new byte[32];
	/**
	 * DC12v2电压开关，开关状态，00关01开
	 */
    public byte[]           szDc12v2State = new byte[32];
	/**
	 * AC24V1电压开关，开关状态，00关01开
	 */
    public byte[]           szAc24v1State = new byte[32];
	/**
	 * AC24V2电压开关，开关状态，00关01开
	 */
    public byte[]           szAc24v2State = new byte[32];
	/**
	 * AC220V1电压开关，开关状态，00关01开
	 */
    public byte[]           szAc220v1State = new byte[32];
	/**
	 * AC220V2电压开关，开关状态，00关01开
	 */
    public byte[]           szAc220v2State = new byte[32];
	/**
	 * AC220V3电压开关，开关状态，00关01开
	 */
    public byte[]           szAc220v3State = new byte[32];
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[256];

	public NET_CASERUNNING_OUTV_INFO() {
	}
}

