package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 自定义物品类型
 * @date 2022/05/11 11:33:24
 */
public class NET_XRAY_INSIDE_ONJECT_TYPE extends NetSDKLib.SdkStructure {
	/**
	 * 物体类型
	 */
    public byte[]           szObjType = new byte[32];
	/**
	 * 保留字节
	 */
    public byte[]           byReserved = new byte[256];

	public NET_XRAY_INSIDE_ONJECT_TYPE() {
	}
}

