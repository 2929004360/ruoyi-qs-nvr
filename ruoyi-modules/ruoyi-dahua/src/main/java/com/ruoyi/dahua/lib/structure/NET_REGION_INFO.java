package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 区间测速信息
 * @date 2022/12/07 20:58:01
 */
public class NET_REGION_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 驶入时间
	 */
    public NET_TIME         stuDriveInTime = new NET_TIME();
	/**
	 * 驶出时间
	 */
    public NET_TIME         stuDriveOutTime = new NET_TIME();
	/**
	 * 保留字节
	 */
    public byte[]           bReserved = new byte[1024];

	public NET_REGION_INFO() {
	}
}

