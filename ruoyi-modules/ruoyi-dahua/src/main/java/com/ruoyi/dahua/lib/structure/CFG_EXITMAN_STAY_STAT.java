package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 离开人员的滞留时间信息
 * @date 2022/12/19 09:10:21
 */
public class CFG_EXITMAN_STAY_STAT extends NetSDKLib.SdkStructure {
	/**
	 * 人员进入区域时间
	 */
    public CFG_NET_TIME_EX  stuEnterTime = new CFG_NET_TIME_EX();
	/**
	 * 人员离开区域时间
	 */
    public CFG_NET_TIME_EX  stuExitTime = new CFG_NET_TIME_EX();
	/**
	 * 保留字节
	 */
    public byte[]           reserved = new byte[104];

	public CFG_EXITMAN_STAY_STAT() {
	}
}

