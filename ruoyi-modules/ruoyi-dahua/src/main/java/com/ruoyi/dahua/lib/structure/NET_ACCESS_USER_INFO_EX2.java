package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 扩展用户信息V2
 * @origin autoTool
 * @date 2023/09/07 19:26:38
 */
public class NET_ACCESS_USER_INFO_EX2 extends NetSDKLib.SdkStructure {
	/**
	 * 平台下发的当前设备的配置信息
	 */
    public byte[]           szProjects = new byte[30 * 64];
	/**
	 *配置信息数量
	 */
    public int              nProjectNum;
	/**
	 * 时间段方案数量
	 */
    public int              nMultiTimeSectionNum;
	/**
	 * 消费时间段
	 */
    public NET_MULTI_TIMESECTION_INFO[] stuMultiTimeSection = new NET_MULTI_TIMESECTION_INFO[128];
	/**
	 * 是否使用szProjectsEx
	 */
    public int              bIsUsingProjectsEx;
    /**
     * 平台下发的当前设备的配置信息(拓展)
     */
    public byte[]           szProjectsEx = new byte[512*64];
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[2048];

	public NET_ACCESS_USER_INFO_EX2() {
		for (int i = 0; i < stuMultiTimeSection.length; i++) {
			stuMultiTimeSection[i] = new NET_MULTI_TIMESECTION_INFO();
		}
	}
}

