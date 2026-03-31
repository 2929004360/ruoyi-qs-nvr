package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 规划式停车场车位信息统计
 * @date 2022/10/28 11:30:56
 */
public class NET_INTELLIPARKING_PLANAREA_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 当前时间点上报车位ID的使用情况个数
	 */
    public int              nIDInfoNum;
	/**
	 * 当前时间点上报车位ID的使用情况集
	 */
    public NET_INTELLIPARKING_PLANAREA_IDINFO[] stIDInfo = new NET_INTELLIPARKING_PLANAREA_IDINFO[32];
	/**
	 * 保留字节
	 */
    public byte[]           reserved = new byte[512];

	public NET_INTELLIPARKING_PLANAREA_INFO() {
		for (int i = 0; i < stIDInfo.length; i++) {
			stIDInfo[i] = new NET_INTELLIPARKING_PLANAREA_IDINFO();
		}
	}
}

