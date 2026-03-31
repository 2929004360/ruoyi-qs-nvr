package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 标定基本信息
 * @date 2023/05/24 10:24:53
 */
public class NET_CALIBRATE_UNIT_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 分辨率高
	 */
    public int              nHeight;
	/**
	 * 分辨率宽
	 */
    public int              nWidth;
	/**
	 * 云台位置(P/T/Z 归一化)
	 */
    public float[]          nPosition = new float[3];
	/**
	 * 标定点坐标
	 */
    public int[]            nLocation = new int[2];
	/**
	 * 横向视场角(单位：0.01度)
	 */
    public int              nHFOV;
	/**
	 * 纵向视场角(单位：0.01度)
	 */
    public int              nVFOV;

	public NET_CALIBRATE_UNIT_INFO() {
	}
}

