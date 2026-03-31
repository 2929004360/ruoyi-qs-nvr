package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 异常防区信息
 * @date 2023/03/15 16:29:32
 */
public class NET_ZONE_ABNORMAL_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 防区号
	 */
    public int              nIndex;
	/**
	 * 防区名称
	 */
    public byte[]           szName = new byte[32];
	/**
	 * 异常原因，异常原因，平台直接取值做显示即可
	 */
    public byte[]           szReason = new byte[32];
	/**
	 * 保留字节
	 */
    public byte[]           byReserved = new byte[188];

	public NET_ZONE_ABNORMAL_INFO() {
	}
}

