package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 统计区报表数据列表
 * @date 2022/10/29 09:36:29
 */
public class NET_PEOPLE_REGION_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 区域名称
	 */
    public byte[]           szRegionName = new byte[128];
	/**
	 * 当前时间点统计区内人数
	 */
    public int              nRegionPeopleNum;
	/**
	 * 保留字节
	 */
    public byte[]           reserved = new byte[1020];

	public NET_PEOPLE_REGION_INFO() {
	}
}

