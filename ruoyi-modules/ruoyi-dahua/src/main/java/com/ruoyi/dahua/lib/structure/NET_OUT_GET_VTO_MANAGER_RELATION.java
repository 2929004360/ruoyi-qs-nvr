package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description CLIENT_GetVTOManagerRelation 出参
 * @origin autoTool
 * @date 2023/10/19 14:16:47
 */
public class NET_OUT_GET_VTO_MANAGER_RELATION extends NetSDKLib.SdkStructure {
	/**
	 * / 结构体大小
	 */
    public int              dwSize;
	/**
	 * / 是否查询完成
	 */
    public int              bIsCompleted;
	/**
	 * / 设备组织树节点信息个数
	 */
    public int              nNodesNum;
	/**
	 * / 设备组织树节点信息
	 */
    public NET_VTO_DEV_NODE_INFO[] stuNodes = new NET_VTO_DEV_NODE_INFO[128];

	public NET_OUT_GET_VTO_MANAGER_RELATION() {
		for (int i = 0; i < stuNodes.length; i++) {
			stuNodes[i] = new NET_VTO_DEV_NODE_INFO();
		}
		this.dwSize = this.size();
	}
}

