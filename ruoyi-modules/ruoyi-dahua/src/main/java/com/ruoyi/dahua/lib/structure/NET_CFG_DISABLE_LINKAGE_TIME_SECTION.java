package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 周期撤防联动项功能总开关
 * @date 2022/10/14 13:53:01
 */
public class NET_CFG_DISABLE_LINKAGE_TIME_SECTION extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 周期撤防联动项功能总开关
	 */
    public int              bEnable;
	/**
	 * 时间段周期设置, 第一维前7个元素对应每周7天，第8个元素对应节假日，每天最多6个时间段
	 */
    public TIME_SECTION_6[] stuTimeSection = new TIME_SECTION_6[NetSDKLib.NET_N_SCHEDULE_TSECT];

	public NET_CFG_DISABLE_LINKAGE_TIME_SECTION() {
		for (int i = 0; i < stuTimeSection.length; i++) {
			stuTimeSection[i] = new TIME_SECTION_6();
		}
		this.dwSize = this.size();
	}
}

