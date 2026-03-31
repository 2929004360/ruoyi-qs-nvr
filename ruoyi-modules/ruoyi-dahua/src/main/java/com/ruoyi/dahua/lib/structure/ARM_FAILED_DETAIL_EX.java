package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 布防失败细节扩展字段
 * @date 2023/03/15 16:29:32
 */
public class ARM_FAILED_DETAIL_EX extends NetSDKLib.SdkStructure {
	/**
	 * 布防失败的区域号
	 */
    public int              nArea;
	/**
	 * 报警源输入返回个数
	 */
    public int              nAlarmSourceRetEx;
	/**
	 * 区域下包含的有源输入的防区
	 */
    public int[]            arrAlarmSourceEx = new int[256];
	/**
	 * 异常防区信息个数
	 */
    public int              nZoneAbnormalNum;
	/**
	 * 异常防区信息，最大有1024个
	 */
    public NET_ZONE_ABNORMAL_INFO[] stuZoneAbnormal = new NET_ZONE_ABNORMAL_INFO[1024];

	public ARM_FAILED_DETAIL_EX() {
		for (int i = 0; i < stuZoneAbnormal.length; i++) {
			stuZoneAbnormal[i] = new NET_ZONE_ABNORMAL_INFO();
		}
	}
}

