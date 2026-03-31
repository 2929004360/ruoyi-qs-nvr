package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description ZIGBEE开关
 * @date 2023/05/11 13:59:24
 */
public class NET_CONTROL_CASE_ZIGBEE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 使能开关
	 */
    public int              bEnable;
	/**
	 * 信道
	 */
    public int              nChannel;
	/**
	 * 节点号
	 */
    public byte[]           szPanID = new byte[64];
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[256];

	public NET_CONTROL_CASE_ZIGBEE_INFO() {
	}
}

