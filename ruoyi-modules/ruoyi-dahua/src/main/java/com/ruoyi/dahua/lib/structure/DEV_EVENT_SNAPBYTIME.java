package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @description 事件类型 EVENT_IVS_SNAPBYTIME(定时抓图事件)对应数据块描述信息
 * @origin autoTool
 * @date 2023/10/26 19:39:31
 */
public class DEV_EVENT_SNAPBYTIME extends NetSDKLib.SdkStructure {
	/**
	 * / 通道号
	 */
    public int              nChannelID;
	/**
	 * / 事件名称
	 */
    public byte[]           szName = new byte[128];
	/**
	 * / 字节对齐
	 */
    public byte[]           bReserved1 = new byte[4];
	/**
	 * / 时间戳(单位是毫秒)
	 */
    public double           PTS;
	/**
	 * / 事件发生的时间
	 */
    public NET_TIME_EX      UTC = new NET_TIME_EX();
	/**
	 * / 事件ID
	 */
    public int              nEventID;
	/**
	 * / 抓图时间
	 */
    public NET_TIME_EX      stuSnapTime = new NET_TIME_EX();
	/**
	 * / 液位值,单位mm
	 */
    public int              uLiquidLevel;
	/**
	 * / 测距值,单位mm
	 */
    public int              uDistMeasure;
	/**
	 * / 温度值,单位℃
	 */
    public int              nTemperature;
	/**
	 * / 湿度值,单位RH
	 */
    public int              nHumidity;
	/**
	 * / 数据个数
	 */
    public int              nDataNum;
	/**
	 * / 数据列表
	 */
    public EVENT_SNAPBYTIME_DATA[] stuDataList = new EVENT_SNAPBYTIME_DATA[20];
	/**
	 * / 图片信息数组, refer to {@link NET_IMAGE_INFO_EX3}
	 */
    public Pointer          pstuImageInfo;
	/**
	 * / 图片信息个数
	 */
    public int              nImageInfoNum;
	/**
	 * null
	 */
    public byte[]           szReversed = new byte[744 - NetSDKLib.POINTERSIZE];

	public DEV_EVENT_SNAPBYTIME() {
		for (int i = 0; i < stuDataList.length; i++) {
			stuDataList[i] = new EVENT_SNAPBYTIME_DATA();
		}
	}
}

