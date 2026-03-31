package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 交换机异常信息
 * @date 2023/05/11 16:00:20
 */
public class NET_CASEABNORMAL_SWITCHWARN_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 告警类型
	 */
    public byte[]           szAlarmType = new byte[32];
	/**
	 * 告警内容
	 */
    public byte[]           szAlarmInfo = new byte[64];
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[128];

	public NET_CASEABNORMAL_SWITCHWARN_INFO() {
	}
}

