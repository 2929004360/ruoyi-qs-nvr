package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 防蜂鸣联动项使能配置信息, 对应 NET_EM_CFG_DISABLE_BEEP_LINKAGE
 * @date 2023/03/15 20:59:42
 */
public class NET_CFG_DISABLE_BEEP_LINKAGE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 开启撤防联动项功能总开关（DisableLinkage）后，是否撤防蜂鸣联动项
	 */
    public int              bEnable;
	/**
	 * 联动项配置名
	 */
    public byte[]           szName = new byte[32];

	public NET_CFG_DISABLE_BEEP_LINKAGE_INFO() {
		this.dwSize = this.size();
	}
}

