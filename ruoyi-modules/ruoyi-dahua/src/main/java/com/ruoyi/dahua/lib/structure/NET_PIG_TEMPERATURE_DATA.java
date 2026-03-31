package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @description 猪体温信息
 * @date 2022/03/31 14:06:49
 */
public class NET_PIG_TEMPERATURE_DATA extends NetSDKLib.SdkStructure {
	/**
	 * 物体位置
	 */
    public NET_RECT         stuRect = new NET_RECT();
	/**
	 * 物体编号
	 */
    public int              nID;
	/**
	 * 最高的温度
	 */
    public float            fMaxTemperature;
	/**
	 * 最低的温度
	 */
    public float            fMinTemperature;
	/**
	 * 平均温度
	 */
    public float            fAverageTemperature;
	/**
	 * 保留字节
	 */
    public byte[]           byReserve = new byte[1024];

	@Override
	public String toString() {
		return "NET_PIG_TEMPERATURE_DATA{" + "stuRect.nLeft=" + stuRect.nLeft + ",stuRect.nTop=" + stuRect.nTop
				+ ",stuRect.nRight=" + stuRect.nRight + ",stuRect.nBottom=" + stuRect.nBottom + ", nID=" + nID
				+ ", fMaxTemperature=" + fMaxTemperature + ", fMinTemperature=" + fMinTemperature
				+ ", fAverageTemperature=" + fAverageTemperature + '}';
	}
}

