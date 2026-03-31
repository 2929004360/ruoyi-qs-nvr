package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 检测目标信息
 * @date 2022/12/19 11:30:59
 */
public class NET_OBJECT_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 目标包围盒(8192坐标系)
	 */
    public NET_RECT         stuBoundingBox = new NET_RECT();
	/**
	 * 保留字节
	 */
    public byte[]           bReserved = new byte[256];

	public NET_OBJECT_INFO() {
	}
}

