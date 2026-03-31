package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_GetSmokeData 出参
 * @date 2022/10/17 13:45:23
 */
public class NET_OUT_GET_SMOKE_DATA extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 温度值
	 */
    public float            ftemperatureData;
	/**
	 * 温度单位 ，参考 NetSDKLib.NET_TEMPERATURE_UNIT
	 */
    public int              emTemperatureUnit;
	/**
	 * 湿度值，单位%RH
	 */
    public int              nHumidityData;
	/**
	 * 一氧化碳浓度 单位：ppm
	 */
    public int              nCOData;
	/**
	 * 甲烷浓度， 单位：%LEL
	 */
    public float            nCH4Data;

	public NET_OUT_GET_SMOKE_DATA() {
		this.dwSize = this.size();
	}
}

