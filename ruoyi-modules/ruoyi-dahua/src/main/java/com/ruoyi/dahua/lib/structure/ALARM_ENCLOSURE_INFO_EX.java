package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @description 电子围栏报警拓展结构体
 * @date 2022/12/06 19:32:32
 */
public class ALARM_ENCLOSURE_INFO_EX extends NetSDKLib.SdkStructure {
	/**
	 * 通道号
	 */
    public int              nChannelID;
	/**
	 * 1:开始 2:停止
	 */
    public int              nAction;
	/**
	 * 事件发生的时间
	 */
    public NET_TIME_EX      stuUTC = new NET_TIME_EX();
	/**
	 * 围栏报警类型 {@link com.ruoyi.dahua.lib.enumeration.EM_ENCLOSURE_TYPE_EX}
	 */
    public int              emType;
	/**
	 * 返回详细类型个数
	 */
    public int              nActionNum;
	/**
	 * 详细类型,参考枚举{@link com.ruoyi.dahua.lib.enumeration.EM_ENCLOSURE_ALARM_TYPE_EX}
	 */
    public int[]            emAction = new int[16];
	/**
	 * 司机编号
	 */
    public int              nDriverNo;
	/**
	 * 围栏编号
	 */
    public int              nEnclosureID;
	/**
	 * 经度(单位是百万分之度,范围0-360度)如东经120.178274度表示为300178274
	 */
    public int              dwLongitude;
	/**
	 * 纬度(单位是百万分之度,范围0-180度)如北纬30.183382度表示为120183382
	 */
    public int              dwLatidude;
	/**
	 * 限制车速
	 */
    public int              nLimitSpeed;
	/**
	 * 当前速度
	 */
    public int              nSpeed;
	/**
	 * 设备ID
	 */
    public byte[]           szDeviceID = new byte[32];
	/**
	 * 出发围栏编号
	 */
    public int              nStartEnclosureID;
	/**
	 * 目的围栏编号
	 */
    public int              nEndEnclosureID;
	/**
	 * 出发时间，本地时间
	 */
    public NET_TIME_EX      stuStartTime = new NET_TIME_EX();
	/**
	 * 出发时间，本地时间
	 */
    public NET_TIME_EX      stuEndTime = new NET_TIME_EX();
	/**
	 * 本趟行驶行驶里程数，单位0.1Km
	 */
    public int              nMileage;
	/**
	 * 趟数
	 */
    public int              nTriggerCount;
	/**
	 * 围栏类型扩展个数
	 */
    public int              nMultiTypeNum;
	/**
	 * 围栏类型扩展
	 */
    public byte[]           szMultiType = new byte[16 * 32];
	/**
	 * GPS信息, refer to {@link NET_GPS_STATUS_INFO}
	 */
    public Pointer          pstuGPSStatus;
	/**
	 * 保留
	 */
    public byte[]           szReserved = new byte[508 - NetSDKLib.POINTERSIZE];

	public ALARM_ENCLOSURE_INFO_EX() {
	}
}

