package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 系统检测状态
 * @date 2023/03/15 21:47:18
 */
public class NET_SYSTEM_STATUS_CHECK_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 检测使能
	 */
    public int              bEnable;
	/**
	 * 禁止布防的条件，当其中一个或多个状态且对应项的值为true时，报警主机应能禁止布防
	 */
    public NET_PREVENTION_OF_ARMING_INFO stuPreventionOfArming = new NET_PREVENTION_OF_ARMING_INFO();
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[512];

	public NET_SYSTEM_STATUS_CHECK_INFO() {
	}
}

