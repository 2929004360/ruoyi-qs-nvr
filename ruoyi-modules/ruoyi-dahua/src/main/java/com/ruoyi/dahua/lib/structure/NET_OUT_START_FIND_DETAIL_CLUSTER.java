package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 接口 CLIENT_StartFindDetailNumberStatCluster 的输出参数
 * @date 2022/10/09 14:05:56
 */
public class NET_OUT_START_FIND_DETAIL_CLUSTER extends NetSDKLib.SdkStructure {
	/**
	 * 此结构体大小,必须赋值
	 */
    public int              dwSize;
	/**
	 * 取到的查询令牌
	 */
    public int              nToken;
	/**
	 * 记录总数
	 */
    public int              nCount;
	/**
	 * 查询该天的营业开始时间
	 */
    public NET_TIME_EX      stuClusterBeginTime = new NET_TIME_EX();
	/**
	 * 查询该天的营业结束时间
	 */
    public NET_TIME_EX      stuClusterEndTime = new NET_TIME_EX();

	public NET_OUT_START_FIND_DETAIL_CLUSTER() {
		this.dwSize = this.size();
	}
}

