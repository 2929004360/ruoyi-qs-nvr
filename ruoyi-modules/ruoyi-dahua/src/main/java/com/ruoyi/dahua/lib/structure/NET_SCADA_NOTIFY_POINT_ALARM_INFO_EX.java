package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 监测点位报警信息
 * @date 2022/12/06 09:04:17
 */
public class NET_SCADA_NOTIFY_POINT_ALARM_INFO_EX extends NetSDKLib.SdkStructure {
	/**
	 * 产生告警时，关联点位的遥测值
	 */
    public float            fEventValue;
	/**
	 * 保留字节
	 */
    public byte[]           szResvered = new byte[1020];

	public NET_SCADA_NOTIFY_POINT_ALARM_INFO_EX() {
	}
}

