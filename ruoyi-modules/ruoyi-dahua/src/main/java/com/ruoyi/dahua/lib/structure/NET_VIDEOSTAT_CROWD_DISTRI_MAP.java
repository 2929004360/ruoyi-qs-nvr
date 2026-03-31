package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 人群分布图报表实时数据
 * @date 2022/10/29 09:36:29
 */
public class NET_VIDEOSTAT_CROWD_DISTRI_MAP extends NetSDKLib.SdkStructure {
	/**
	 * 时间
	 */
    public NET_TIME_EX      UTC = new NET_TIME_EX();
	/**
	 * 通道号
	 */
    public int              nChannelID;
	/**
	 * 预置点编号
	 */
    public int              nPtzPresetId;
	/**
	 * 统计区个数
	 */
    public int              nRegionNum;
	/**
	 * 统计区列表, 实际个数和nRegionNum保持一致
	 */
    public NET_PEOPLE_REGION_INFO[] stuRegionList = new NET_PEOPLE_REGION_INFO[16];
	/**
	 * 保留字节
	 */
    public byte[]           reserved = new byte[1024];

	public NET_VIDEOSTAT_CROWD_DISTRI_MAP() {
		for (int i = 0; i < stuRegionList.length; i++) {
			stuRegionList[i] = new NET_PEOPLE_REGION_INFO();
		}
	}
}

