package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_DeleteWorkSuitCompareGroup 接口输出参数
 * @date 2022/10/08 17:14:26
 */
public class NET_OUT_DELETE_WORKSUIT_COMPARE_GROUP extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;

	public NET_OUT_DELETE_WORKSUIT_COMPARE_GROUP() {
		this.dwSize = this.size();
	}
}

