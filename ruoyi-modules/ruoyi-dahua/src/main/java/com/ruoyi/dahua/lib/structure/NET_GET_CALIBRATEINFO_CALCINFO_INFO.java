package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 标定点位置信息
 * @date 2023/05/24 10:24:51
 */
public class NET_GET_CALIBRATEINFO_CALCINFO_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 标定点坐标及倍率
	 */
    public float[]          fPosition = new float[3];
	/**
	 * 标定点在图像中坐标
	 */
    public int[]            nLocation = new int[2];

	public NET_GET_CALIBRATEINFO_CALCINFO_INFO() {
	}
}

