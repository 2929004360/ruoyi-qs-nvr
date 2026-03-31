package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @description 设置配件信息入参(对应DH_CTRL_LOWRATEWPAN_SET_ACCESSORY_PARAM)
 * @date 2022/08/31 14:44:18
 */
public class NET_IN_CTRL_LOWRATEWPAN_ACCESSORY_PARAM extends NetSDKLib.SdkStructure {
    public int              dwSize;
	/**
	 * 配件信息
	 */
    public NET_WPAN_ACCESSORY_INFO stuInfo = new NET_WPAN_ACCESSORY_INFO();

	public NET_IN_CTRL_LOWRATEWPAN_ACCESSORY_PARAM() {
		this.dwSize = this.size();
	}
}

