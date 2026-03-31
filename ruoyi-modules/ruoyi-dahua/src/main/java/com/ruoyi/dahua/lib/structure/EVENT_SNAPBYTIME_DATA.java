package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 用户数据
 * @origin autoTool
 * @date 2023/10/26 19:39:30
 */
public class EVENT_SNAPBYTIME_DATA extends NetSDKLib.SdkStructure {
	/**
	 * / 液位值,单位mm
	 */
    public int              dwLiquidLevel;
	/**
	 * / 测距值,单位mm
	 */
    public int              dwDistMeasure;
	/**
	 * / 温度值,单位℃
	 */
    public int              nTemperature;
	/**
	 * / 湿度值,单位RH
	 */
    public int              nHumidity;
	/**
	 * / 流量值,单位m^3
	 */
    public int              dwWaterFlow;
	/**
	 * / 报警状态： 正常 FALSE, 报警 TRUE
	 */
    public int              bAlarmFlag;
	/**
	 * / 保留字节
	 */
    public byte[]           szReversed = new byte[40];

	public EVENT_SNAPBYTIME_DATA() {
	}
}

