package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 事件类型 EVENT_IVS_HEAT_IMAGING_TEMPER对应的数据描述信息
 * @date 2022/06/07 14:08:53
 */
public class DEV_EVENT_HEAT_IMAGING_TEMPER_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 温度异常点名称 从测温规则配置项中选择
	 */
    public byte[]           szName = new byte[64];
	/**
	 * 报警项编号
	 */
    public int              nAlarmId;
	/**
	 * 报警结果值 fTemperatureValue 的类型
	 * {@link com.ruoyi.dahua.lib.enumeration.NET_RADIOMETRY_RESULT}
	 */
    public int              emResult;
	/**
	 * 报警条件 {@link com.ruoyi.dahua.lib.enumeration.NET_RADIOMETRY_ALARMCONTION}
	 */
    public int              emAlarmContion;
	/**
	 * 报警温度值
	 */
    public float            fTemperatureValue;
	/**
	 * 温度单位(当前配置的温度单位) {@link NetSDKLib.NET_TEMPERATURE_UNIT }
	 */
    public int              emTemperatureUnit;
	/**
	 * 报警点的坐标 相对坐标体系,取值均为0~8191
	 */
    public NetSDKLib.NET_POINT stCoordinate = new NetSDKLib.NET_POINT();
	/**
	 * 预置点
	 */
    public int              nPresetID;
	/**
	 * 通道号
	 */
    public int              nChannel;
	/**
	 * 0:脉冲 1:开始 2:停止
	 */
    public int              nAction;
	/**
	 * 报警坐标, 其类型可以是点，线或多边形。替换stCoordinate字段
	 */
    public NetSDKLib.NET_POLY_POINTS stuAlarmCoordinates = new NetSDKLib.NET_POLY_POINTS();
	/**
	 * szNameEx 是否有效，为TRUE时，使用szNameEx字段
	 */
    public int              bUseNameEx;
	/**
	 * 字节对齐
	 */
    public byte[]           reserved1 = new byte[4];
	/**
	 * 温度异常点名称扩展
	 */
    public byte[]           szNameEx = new byte[128];
    /**
     * 事件公共扩展字段结构体
     */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
    public byte[]           reserved = new byte[884];

	public DEV_EVENT_HEAT_IMAGING_TEMPER_INFO() {
	}
}

