package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 猪只离开滞留时间信息
 * @date 2022/12/19 09:10:21
 */
public class CFG_PIG_STAY_STAT extends NetSDKLib.SdkStructure {
	/**
	 * 猪只进入区域的时间
	 */
    public CFG_NET_TIME     stuEnterTime = new CFG_NET_TIME();
	/**
	 * 猪只离开区域的时间
	 */
    public CFG_NET_TIME     stuExitTime = new CFG_NET_TIME();
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[200];

	public CFG_PIG_STAY_STAT() {
	}
}

