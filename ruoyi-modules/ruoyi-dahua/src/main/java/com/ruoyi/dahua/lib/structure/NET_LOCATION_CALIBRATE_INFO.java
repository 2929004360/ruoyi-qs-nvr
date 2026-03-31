package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 设备标定信息
 * @date 2023/05/24 10:24:53
 */
public class NET_LOCATION_CALIBRATE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 可见光横向视角
	 */
    public int              nVisualMaxHFOV;
	/**
	 * 可见光纵向视角
	 */
    public int              nVisualMaxVFOV;
	/**
	 * 热成像横向视角
	 */
    public int              nThermoMaxHFOV;
	/**
	 * 热成像纵向视角
	 */
    public int              nThermoMaxVFOV;
	/**
	 * 标定点信息
	 */
    public NET_LOCATION_CALIBRATE_POINT_INFO[] stuPointInfo = new NET_LOCATION_CALIBRATE_POINT_INFO[64];
	/**
	 * 标定点个数
	 */
    public int              nPointNum;
	/**
	 * 字节对齐
	 */
    public byte[]           byReserved1 = new byte[4];

	public NET_LOCATION_CALIBRATE_INFO() {
		for (int i = 0; i < stuPointInfo.length; i++) {
			stuPointInfo[i] = new NET_LOCATION_CALIBRATE_POINT_INFO();
		}
	}
}

