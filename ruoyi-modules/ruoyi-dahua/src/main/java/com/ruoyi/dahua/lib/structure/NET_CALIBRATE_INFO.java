package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 标定详细信息
 * @date 2023/05/24 10:24:53
 */
public class NET_CALIBRATE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 标定点基本信息
	 */
    public NET_CALIBRATE_UNIT_INFO[] stuCalibrateUnitInfo = new NET_CALIBRATE_UNIT_INFO[5 * 2];
	/**
	 * 基本信息数量
	 */
    public int              nInfoNum;

	public NET_CALIBRATE_INFO() {
		for (int i = 0; i < stuCalibrateUnitInfo.length; i++) {
			stuCalibrateUnitInfo[i] = new NET_CALIBRATE_UNIT_INFO();
		}
	}
}

