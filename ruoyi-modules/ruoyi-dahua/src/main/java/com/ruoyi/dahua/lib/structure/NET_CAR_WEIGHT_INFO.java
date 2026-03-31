package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 称重系统车辆信息
 * @date 2023/04/07 17:11:22
 */
public class NET_CAR_WEIGHT_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 车轴数
	 */
    public int              nAxleNum;
	/**
	 * 最大轴距, 单位是毫米
	 */
    public int              nMaxAxleDistance;
	/**
	 * 每根车轴的重量, 单位Kg, 第一个元素表示第一个车轴重, 依次类推
	 */
    public int[]            nAxleWeightInfo = new int[8];
	/**
	 * 轴与轴的间隔, 单位毫米, 第一个元素表示一二轴间距, 第二个元素表示二三轴间距, 依次类推
	 */
    public int[]            nAxleDistanceInfo = new int[8 - 1];
	/**
	 * 超重信息, 单位千克
	 */
    public int              nOverWeight;
	/**
	 * 总重信息, 单位千克
	 */
    public int              nTotalWeight;
	/**
	 * 平板称重器提供的轴型信息
	 */
    public int              nAxisType;
	/**
	 * 称重卡口测量上报的速度, 单位km/h
	 */
    public int              nSpeed;
	/**
	 * 保留字节
	 */
    public byte[]           byReserved = new byte[500];

	public NET_CAR_WEIGHT_INFO() {
	}
}

