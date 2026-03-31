package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @description 无线设备低电量报警事件结构体
 * @date 2022/07/19 10:21:10
 */
public class ALARM_WIRELESSDEV_LOWPOWER_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 是否为低电量true低电量,false 电量正常,unknown未知 {@link NetSDKLib.NET_THREE_STATUS_BOOL}
	 */
    public int              emResult;
	/**
	 * 事件发生的时间
	 */
    public NET_TIME         stuTime = new NET_TIME();
	/**
	 * 无线设备ID 此字段协议上已废弃
	 */
    public int              nId;
	/**
	 * 无线设备类型 {@link com.ruoyi.dahua.lib.enumeration.NET_WIRELESSDEV_LOWPOWER_TYPE}
	 */
    public int              emType;
	/**
	 * 无线配件序列号
	 */
    public byte[]           szSN = new byte[32];
	/**
	 * 电量百分比
	 */
    public float            fPercent;
	/**
	 * 通道号或探测器地址
	 */
    public int              nIndex;
	/**
	 * 事件公共扩展字段结构体
	 */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
	/**
	 * 预留
	 */
    public byte[]           reserved = new byte[984];

	public ALARM_WIRELESSDEV_LOWPOWER_INFO() {
	}
}

