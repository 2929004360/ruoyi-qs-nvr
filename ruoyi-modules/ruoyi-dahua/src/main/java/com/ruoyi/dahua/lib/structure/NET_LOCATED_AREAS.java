package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 目标当前所处的区域信息
 * @origin autoTool
 * @date 2023/06/13 10:35:34
 */
public class NET_LOCATED_AREAS extends NetSDKLib.SdkStructure {
	/**
	 * 字符串格式的区域ID
	 */
    public byte[]           szIDStr = new byte[16];
	/**
	 * 区域类型 0:未知 1：路口 2：路段
	 */
    public int              nType;
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[60];

	public NET_LOCATED_AREAS() {
	}
}

