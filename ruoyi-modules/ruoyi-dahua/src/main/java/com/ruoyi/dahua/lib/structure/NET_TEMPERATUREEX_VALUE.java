package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 每个监测点的温度
 * @date 2022/05/12 16:32:08
 */
public class NET_TEMPERATUREEX_VALUE extends NetSDKLib.SdkStructure {
	/**
	 * 温度类型 {@link com.ruoyi.dahua.lib.enumeration.EM_TEMPERATUREEX_TYPE}
	 */
    public int              emTemperatureType;
	/**
	 * 返回的有效温度值个数
	 */
    public int              nRetTemperatureNum;
	/**
	 * 温度值,单位:摄氏度
	 */
    public float[]          fTemperature = new float[64];
	/**
	 * 保留字节
	 */
    public byte[]           byReserved = new byte[128];

	public NET_TEMPERATUREEX_VALUE() {
	}
}

