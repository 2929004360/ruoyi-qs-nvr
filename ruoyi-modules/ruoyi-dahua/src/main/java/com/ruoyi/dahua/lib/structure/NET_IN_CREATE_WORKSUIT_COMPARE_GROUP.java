package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_CreateWorkSuitCompareGroup 接口输入参数
 * @date 2022/10/08 17:05:54
 */
public class NET_IN_CREATE_WORKSUIT_COMPARE_GROUP extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 字节对齐
	 */
    public byte[]           szReserved = new byte[4];
	/**
	 * 工装合规组信息
	 */
    public NET_WORKSUIT_COMPARE_GROUP_INFO stuWorkSuitGroupInfo = new NET_WORKSUIT_COMPARE_GROUP_INFO();

	public NET_IN_CREATE_WORKSUIT_COMPARE_GROUP() {
		this.dwSize = this.size();
	}
}

