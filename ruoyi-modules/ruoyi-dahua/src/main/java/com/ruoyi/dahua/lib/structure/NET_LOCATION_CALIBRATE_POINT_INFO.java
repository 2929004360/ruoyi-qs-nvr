package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 标定点信息
 * @date 2023/05/24 10:24:53
 */
public class NET_LOCATION_CALIBRATE_POINT_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 标定点ID
	 */
    public int              nID;
	/**
	 * 标定点名称
	 */
    public byte[]           szName = new byte[128];
	/**
	 * 使能
	 */
    public int              bEnable;
	/**
	 * 经度
	 */
    public int              nLongitude;
	/**
	 * 纬度
	 */
    public int              nLatitude;
	/**
	 * 海拔
	 */
    public double           fAltitude;
	/**
	 * 标定点基本信息
	 */
    public NET_CALIBRATE_INFO stuCalibrateInfo = new NET_CALIBRATE_INFO();
	/**
	 * 字节对齐
	 */
    public byte[]           byReserved1 = new byte[4];

	public NET_LOCATION_CALIBRATE_POINT_INFO() {
	}
}

