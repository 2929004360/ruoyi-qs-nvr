package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 接口 CLIENT_GetClusterState 的输入参数
 * @date 2022/10/09 14:24:24
 */
public class NET_IN_GET_CLUSTER_STATE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 此结构体大小,必须赋值
	 */
    public int              dwSize;
	/**
	 * 非必选, 当包含该字段时查对应日期的聚档状态, 否则返回最近一次的状态
	 */
    public int              nClusterDate;

	public NET_IN_GET_CLUSTER_STATE_INFO() {
		this.dwSize = this.size();
	}
}

