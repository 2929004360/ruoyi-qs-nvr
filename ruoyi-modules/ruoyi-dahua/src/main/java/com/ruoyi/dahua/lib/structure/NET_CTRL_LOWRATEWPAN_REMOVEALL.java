package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 删除全部无线设备 CLIENT_ControlDevice接口的
 *              DH_CTRL_LOWRATEWPAN_REMOVEALL命令参数
 * @date 2023/03/16 15:39:22
 */
public class NET_CTRL_LOWRATEWPAN_REMOVEALL extends NetSDKLib.SdkStructure {
    public int              dwSize;

	public NET_CTRL_LOWRATEWPAN_REMOVEALL() {
		this.dwSize = this.size();
	}
}

