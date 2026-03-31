package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description Area布撤防配置, 对应 NET_EM_CFG_AREA_ARM_MODE
 * @date 2023/03/15 21:47:18
 */
public class NET_CFG_AREA_ARM_MODE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * Area状态信息数量，数组最大64个
	 */
    public int              nAreasNum;
	/**
	 * Area状态信息数组
	 */
    public NET_AREAS_INFO[] stuAreas = new NET_AREAS_INFO[64];
	/**
	 * 系统检测状态
	 */
    public NET_SYSTEM_STATUS_CHECK_INFO stuSystemStatusCheck = new NET_SYSTEM_STATUS_CHECK_INFO();

	public NET_CFG_AREA_ARM_MODE_INFO() {
		for (int i = 0; i < stuAreas.length; i++) {
			stuAreas[i] = new NET_AREAS_INFO();
		}
		this.dwSize = this.size();
	}
}

