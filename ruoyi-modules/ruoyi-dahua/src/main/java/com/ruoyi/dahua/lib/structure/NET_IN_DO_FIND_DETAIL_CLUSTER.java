package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 接口 CLIENT_DoFindDetailNumberStatCluster 的输入参数
 * @date 2022/10/09 14:08:11
 */
public class NET_IN_DO_FIND_DETAIL_CLUSTER extends NetSDKLib.SdkStructure {
	/**
	 * 此结构体大小,必须赋值
	 */
    public int              dwSize;
	/**
	 * start接口返回的token, token保存的数量有限, 未stop的会在连接销毁时或队列满时被释放, 再用同一token访问会导致失败
	 */
    public int              nToken;
	/**
	 * 查询记录的开始偏移
	 */
    public int              nStartIndex;
	/**
	 * 查询的记录数，单次不超过1024
	 */
    public int              nCount;

	public NET_IN_DO_FIND_DETAIL_CLUSTER() {
		this.dwSize = this.size();
	}
}

