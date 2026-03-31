package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 驻留报警
 * @date 2023/01/10 21:09:08
 */
public class NET_CFG_MINIRADAR_RESIDENTALARM extends NetSDKLib.SdkStructure {
	/**
	 * 使能开关
	 */
    public int              bEnable;
	/**
	 * 单位秒，驻留时间，超过该时间就报警
	 */
    public int              nTime;
	/**
	 * 单位秒，报警输出延迟时间
	 */
    public int              nDelay;
	/**
	 * 关联报警输出端口，每一bit代表一路报警输出
	 */
    public int              nAlarmOutMask;
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[1024];

	public NET_CFG_MINIRADAR_RESIDENTALARM() {
	}
}

