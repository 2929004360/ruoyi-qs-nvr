package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 接口 CLIENT_DoFindDetailNumberStatCluster 的输出参数
 * @date 2022/10/09 14:08:24
 */
public class NET_OUT_DO_FIND_DETAIL_CLUSTER extends NetSDKLib.SdkStructure {
	/**
	 * 此结构体大小,必须赋值
	 */
    public int              dwSize;
	/**
	 * 实际查询返回的记录数
	 */
    public int              nFound;
	/**
	 * 返回记录实际条数
	 */
    public int              nFindInfoRealNum;
	/**
	 * 返回的记录，单次不超过1024
	 */
    public NET_CLUSTER_DETAIL_FIND_INFO[] stuClusterFindInfo = new NET_CLUSTER_DETAIL_FIND_INFO[1024];

	public NET_OUT_DO_FIND_DETAIL_CLUSTER() {
		for (int i = 0; i < stuClusterFindInfo.length; i++) {
			stuClusterFindInfo[i] = new NET_CLUSTER_DETAIL_FIND_INFO();
		}
		this.dwSize = this.size();
	}
}

