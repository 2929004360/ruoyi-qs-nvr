package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 电压状态
 * @date 2023/05/11 16:03:12
 */
public class NET_STATEMANAGER_V_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 电压值，单位：v
	 */
    public byte[]           szV = new byte[32];
	/**
	 * 电流值，单位：c
	 */
    public byte[]           szC = new byte[32];
	/**
	 * 功率值，单位：w
	 */
    public byte[]           szP = new byte[32];
	/**
	 * 电能值，单位：w.h
	 */
    public byte[]           szW = new byte[32];
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[128];

	public NET_STATEMANAGER_V_INFO() {
	}
}

