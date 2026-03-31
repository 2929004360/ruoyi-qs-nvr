package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @description GPS坐标
 * @origin autoTool
 * @date 2023/02/14 20:02:02
 */
public class NET_GPS_POSITION extends NetSDKLib.SdkStructure {
	/**
	 * 经度(单位是百万分之度,范围0-360度)
	 */
    public double           dbLongitude;
	/**
	 * 纬度(单位是百万分之度,范围0-180度)
	 */
    public double           dbLatitude;
	/**
	 * 海拔(单位：米)
	 */
    public double           dbAltitude;
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[128];

	public NET_GPS_POSITION() {
	}

	@Override
	public String toString() {
		return "{" + "dbLongitude=" + dbLongitude + ", dbLatitude=" + dbLatitude + ", dbAltitude=" + dbAltitude + '}';
	}
}

