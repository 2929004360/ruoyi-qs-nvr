package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 智能动检(车)对象信息
 * @date 2023/05/15 13:49:36
 */
public class NET_SMARTDETECT_VEHICLE_OBJECT extends NetSDKLib.SdkStructure {
	/**
	 * 车动检ID
	 */
    public int              nVehicleID;
	/**
	 * 车的位置
	 */
    public NET_RECT         stuRect = new NET_RECT();
	/**
	 * 保留字节
	 */
    public byte[]           bReserved = new byte[508];

	public NET_SMARTDETECT_VEHICLE_OBJECT() {
	}
}

