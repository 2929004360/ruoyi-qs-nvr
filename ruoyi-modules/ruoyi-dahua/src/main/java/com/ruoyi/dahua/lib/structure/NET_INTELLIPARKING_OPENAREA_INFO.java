package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 规划式停车场车位信息统计
 * @date 2022/10/28 11:30:56
 */
public class NET_INTELLIPARKING_OPENAREA_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 车位对应的ID
	 */
    public int              nID;
	/**
	 * 停车场中停车数
	 */
    public int              nParkingNum;
	/**
	 * 保留字节
	 */
    public byte[]           reserved = new byte[512];

	public NET_INTELLIPARKING_OPENAREA_INFO() {
	}
}

