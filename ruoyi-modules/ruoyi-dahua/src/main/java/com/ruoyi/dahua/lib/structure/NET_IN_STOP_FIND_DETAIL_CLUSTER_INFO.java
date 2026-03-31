package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 接口 CLIENT_StopFindDetailNumberStatCluster 的输入参数
 * @date 2022/10/09 14:22:07
 */
public class NET_IN_STOP_FIND_DETAIL_CLUSTER_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 此结构体大小,必须赋值
	 */
    public int              dwSize;
	/**
	 * start接口返回的token, token仅会保存
	 */
    public int              nToken;

	public NET_IN_STOP_FIND_DETAIL_CLUSTER_INFO() {
		this.dwSize = this.size();
	}
}

