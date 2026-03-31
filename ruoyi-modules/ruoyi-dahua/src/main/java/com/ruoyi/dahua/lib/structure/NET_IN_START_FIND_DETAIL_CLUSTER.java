package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 接口 CLIENT_StartFindDetailNumberStatCluster 的输入参数
 * @date 2022/10/09 14:06:13
 */
public class NET_IN_START_FIND_DETAIL_CLUSTER extends NetSDKLib.SdkStructure {
	/**
	 * 此结构体大小,必须赋值
	 */
    public int              dwSize;
	/**
	 * 查询条件
	 */
    public NET_CLUSTER_DETAIL_CONDITION stuCondition = new NET_CLUSTER_DETAIL_CONDITION();
    /**
     * 需要查询的级联通道，从0开始；带有此字段代表透传级联前端，不带此字段代表查询当前设备
    */
    public int              nChannel;
    /**
     * 是否支持下发级联通道字段
    */
    public int              bIsSupportChannel;

	public NET_IN_START_FIND_DETAIL_CLUSTER() {
		this.dwSize = this.size();
	}
}

