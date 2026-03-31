package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 事件类型 EVENT_IVS_HOTSPOT_WARNING (热点异常报警事件) 对应的数据块描述信息
 * @date 2023/05/24 10:24:54
 */
public class DEV_EVENT_HOTSPOT_WARNING_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 通道号
	 */
    public int              nChannelID;
	/**
	 * 0:脉冲 1:开始 2:停止
	 */
    public int              nAction;
	/**
	 * 事件名称
	 */
    public byte[]           szName = new byte[128];
	/**
	 * 时间戳(单位是毫秒)
	 */
    public double           PTS;
	/**
	 * 事件发生的时间
	 */
    public NET_TIME_EX      UTC = new NET_TIME_EX();
	/**
	 * 事件对应文件信息
	 */
    public NetSDKLib.NET_EVENT_FILE_INFO stuFileInfo = new NetSDKLib.NET_EVENT_FILE_INFO();
	/**
	 * 事件ID
	 */
    public int              nEventID;
	/**
	 * 热点的坐标,坐标值 0~8191
	 */
    public NetSDKLib.DH_POINT stuPoint = new NetSDKLib.DH_POINT();
	/**
	 * 热点温度值
	 */
    public float            fHotSpotValue;
	/**
	 * 温度单位(当前配置的温度单位),{@link NetSDKLib.NET_TEMPERATURE_UNIT}
	 */
    public int              nTemperatureUnit;
    /**
     * 热点报警阈值
    */
    public float            fThreshold;
	/**
	 * 保留字节
	 */
    public byte[]           byReserved = new byte[252];

	public DEV_EVENT_HOTSPOT_WARNING_INFO() {
	}
}

