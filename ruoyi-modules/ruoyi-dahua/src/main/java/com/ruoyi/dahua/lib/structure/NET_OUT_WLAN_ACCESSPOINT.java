package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_QueryDevInfo , NET_QUERY_WLAN_ACCESSPOINT 命令输出参数
 * @date 2023/03/16 14:10:09
 */
public class NET_OUT_WLAN_ACCESSPOINT extends NetSDKLib.SdkStructure {
    public int              dwSize;
	/**
	 * 无线网络接入点个数
	 */
    public int              nCount;
	/**
	 * 接入点信息
	 */
    public NET_WLAN_ACCESSPOINT_INFO[] stuInfo = new NET_WLAN_ACCESSPOINT_INFO[128];

	public NET_OUT_WLAN_ACCESSPOINT() {
		for (int i = 0; i < stuInfo.length; i++) {
			stuInfo[i] = new NET_WLAN_ACCESSPOINT_INFO();
		}
		this.dwSize = this.size();
	}
}

