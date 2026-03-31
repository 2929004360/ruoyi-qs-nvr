package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 删除指定无线设备 CLIENT_ControlDevice 接口的 DH_CTRL_LOWRATEWPAN_REMOVE命令参数
 * @date 2023/03/16 15:38:18
 */
public class NET_CTRL_LOWRATEWPAN_REMOVE extends NetSDKLib.SdkStructure {
    public int              dwSize;
	/**
	 * 无线设备ID
	 */
    public long             nWirelessId;
	/**
	 * 无线设备序列号 无线对码后续采用序列号的方式
	 */
    public byte[]           szSerialNumber = new byte[32];

	public NET_CTRL_LOWRATEWPAN_REMOVE() {
		this.dwSize = this.size();
	}
}

