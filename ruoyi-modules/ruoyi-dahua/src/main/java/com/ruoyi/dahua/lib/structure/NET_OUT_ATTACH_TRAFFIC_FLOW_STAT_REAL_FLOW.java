package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description CLIENT_AttachTrafficFlowStatRealFlow 输出参数
 * @origin autoTool
 * @date 2023/08/31 14:21:53
 */
public class NET_OUT_ATTACH_TRAFFIC_FLOW_STAT_REAL_FLOW extends NetSDKLib.SdkStructure {
	/**
	 * 此结构体大小,必须赋值
	 */
    public int              dwSize;

	public NET_OUT_ATTACH_TRAFFIC_FLOW_STAT_REAL_FLOW() {
		this.dwSize = this.size();
	}
}

