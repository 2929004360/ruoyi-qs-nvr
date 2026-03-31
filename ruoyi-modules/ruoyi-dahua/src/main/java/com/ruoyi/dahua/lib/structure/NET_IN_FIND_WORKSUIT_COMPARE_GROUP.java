package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_FindWorkSuitCompareGroup 接口输入参数
 * @date 2022/10/10 13:40:26
 */
public class NET_IN_FIND_WORKSUIT_COMPARE_GROUP extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 字节对齐
	 */
    public byte[]           szReserved = new byte[4];
	/**
	 * 合规库组ID, 不填组ID表示查找全部组信息
	 */
    public byte[]           szGroupID = new byte[64];

	public NET_IN_FIND_WORKSUIT_COMPARE_GROUP() {
		this.dwSize = this.size();
	}
}

