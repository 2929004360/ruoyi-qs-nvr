package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description CLIENT_SetVTOManagerRelation 入参
 * @origin autoTool
 * @date 2023/10/19 14:16:46
 */
public class NET_IN_SET_VTO_MANAGER_RELATION extends NetSDKLib.SdkStructure {
	/**
	 * / 结构体大小
	 */
    public int              dwSize;
	/**
	 * / 关系节点信息个数
	 */
    public int              nNodeInfoNum;
	/**
	 * / 关系节点信息
	 */
    public NET_VTO_DEV_NODE_INFO[] stuNodes = new NET_VTO_DEV_NODE_INFO[128];
	/**
	 * / 是否需要清除原配置
	 */
    public int              bNeedReset;

	public NET_IN_SET_VTO_MANAGER_RELATION() {
		for (int i = 0; i < stuNodes.length; i++) {
			stuNodes[i] = new NET_VTO_DEV_NODE_INFO();
		}
		this.dwSize = this.size();
	}
}

