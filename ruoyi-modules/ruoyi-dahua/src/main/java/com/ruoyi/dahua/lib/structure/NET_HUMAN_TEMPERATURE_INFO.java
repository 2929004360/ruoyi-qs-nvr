package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 人体温信息
 * @date 2022/11/24 21:06:12
 */
public class NET_HUMAN_TEMPERATURE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 温度
	 */
    public double           dbTemperature;
	/**
	 * 温度单位 { @link EM_HUMAN_TEMPERATURE_UNIT}
	 */
    public int              emTemperatureUnit;
	/**
	 * 是否超温
	 */
    public int              bIsOverTemp;
	/**
	 * 是否低的温度
	 */
    public int              bIsUnderTemp;
	/**
	 * 预留字段
	 */
    public byte[]           bReserved = new byte[132];

	public NET_HUMAN_TEMPERATURE_INFO() {
	}
}

