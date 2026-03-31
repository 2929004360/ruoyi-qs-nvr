package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 增加对码信息
 * @date 2023/03/16 14:52:53
 */
public class NET_CTRL_LOWRATEWPAN_ADD extends NetSDKLib.SdkStructure {
    public int              dwSize;
	/**
	 * 对码数据
	 */
    public NetSDKLib.NET_CODEID_INFO stuCodeIDInfo = new NetSDKLib.NET_CODEID_INFO();

	public NET_CTRL_LOWRATEWPAN_ADD() {
		this.dwSize = this.size();
	}
}

