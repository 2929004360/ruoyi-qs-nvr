package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description CLIENT_RadarManualTrack接口出参
 * @date 2021/10/22 9:03
 */
public class NET_OUT_RADAR_MANUAL_TRACK extends NetSDKLib.SdkStructure {
	/** 结构体大小 */
    public int              dwSize;
	/** 返回被跟踪轨迹对应的球机ip */
    public byte[]           szSDIP = new byte[32];
	/**返回被跟踪目标的一个全局唯一id */
    public int              nEventID;
	/** 字节对齐 **/
    public int[]            szReserved = new int[4];
	/** 联动目标唯一id；（多雷达情况下也是唯一） char数组的第1-12位： 是设备的MAC地址； 13-23位 ： 是时间； 23-28位 ：是随机数； 29-32位 ：是目标id，即TrackID字段 **/
    public byte[]           szTargetUUID = new byte[32];

	public NET_OUT_RADAR_MANUAL_TRACK() {
		this.dwSize = this.size();
	}
}

