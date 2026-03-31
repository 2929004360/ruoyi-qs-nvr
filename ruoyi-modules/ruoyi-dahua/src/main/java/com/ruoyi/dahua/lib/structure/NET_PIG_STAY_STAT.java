package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 猪只离开开滞留时间信息
 * @date 2022/09/21 14:54:45
 */
public class NET_PIG_STAY_STAT extends NetSDKLib.SdkStructure {
	/**
	 * 猪只进入区域的时间
	 */
    public NET_TIME         stuEnterTime = new NET_TIME();
	/**
	 * 猪只离开区域的时间
	 */
    public NET_TIME         stuExitTime = new NET_TIME();
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[208];

	public NET_PIG_STAY_STAT() {
	}
}

