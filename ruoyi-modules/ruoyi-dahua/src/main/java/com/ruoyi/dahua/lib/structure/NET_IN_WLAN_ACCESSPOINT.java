package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_QueryDevInfo , NET_QUERY_WLAN_ACCESSPOINT 命令输入参数
 * @date 2023/03/16 14:09:33
 */
public class NET_IN_WLAN_ACCESSPOINT extends NetSDKLib.SdkStructure {
    public int              dwSize;
	/**
	 * 需要获取信息的无线网络名称,为空时搜索所有网络
	 */
    public byte[]           szSSID = new byte[36];
	/**
	 * 网卡名称, 为空时, 默认为eth2
	 */
    public byte[]           szName = new byte[32];

	public NET_IN_WLAN_ACCESSPOINT() {
		this.dwSize = this.size();
	}
}

