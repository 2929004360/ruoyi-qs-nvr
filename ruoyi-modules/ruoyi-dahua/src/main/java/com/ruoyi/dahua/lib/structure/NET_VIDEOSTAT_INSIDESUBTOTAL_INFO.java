package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 目标实时检测位置统计
 * @date 2022/10/28 11:06:44
 */
public class NET_VIDEOSTAT_INSIDESUBTOTAL_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 当前时间点上报人数的横坐标集，坐标体系是1024体系,数组每个元素的范围为[0, 1023]
	 */
    public int[]            nPosXs = new int[32];
	/**
	 * nPoxXs数组有效数据个数
	 */
    public int              nPoxXsNum;
	/**
	 * 预留
	 */
    public byte[]           byReserved = new byte[988];

	public NET_VIDEOSTAT_INSIDESUBTOTAL_INFO() {
	}
}

