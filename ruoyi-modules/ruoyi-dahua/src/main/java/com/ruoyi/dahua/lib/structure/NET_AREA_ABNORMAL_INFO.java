package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 失败详情
 * @origin autoTool
 * @date 2023/08/10 09:52:29
 */
public class NET_AREA_ABNORMAL_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 布防失败的Area，1-64
	 */
    public int              nArea;
	/**
	 * Area下包含的有源输入的防区数组有效个数
	 */
    public int              nAlarmSourceNum;
	/**
	 * Area下包含的有源输入的防区
	 */
    public int[]            nAlarmSource = new int[256];
	/**
	 * 异常防区信息数组有效个数
	 */
    public int              nZoneAbnormalNum;
	/**
	 * 异常防区信息
	 */
    public NET_ARM_MODE_ZONE_ABNORMAL_INFO[] stuZoneAbnormal = new NET_ARM_MODE_ZONE_ABNORMAL_INFO[1024];
	/**
	 * 保留字节
	 */
    public byte[]           szResvered = new byte[128];

	public NET_AREA_ABNORMAL_INFO() {
		for (int i = 0; i < stuZoneAbnormal.length; i++) {
			stuZoneAbnormal[i] = new NET_ARM_MODE_ZONE_ABNORMAL_INFO();
		}
	}
}

