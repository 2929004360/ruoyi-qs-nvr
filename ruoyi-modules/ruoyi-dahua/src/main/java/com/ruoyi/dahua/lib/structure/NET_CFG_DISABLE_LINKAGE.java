package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 周期撤防联动项功能总开关
 * @date 2022/10/14 13:46:58
 */
public class NET_CFG_DISABLE_LINKAGE extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 撤防联动项功能总开关
	 */
    public int              bEnable;

	public NET_CFG_DISABLE_LINKAGE() {
		this.dwSize = this.size();
	}
}

