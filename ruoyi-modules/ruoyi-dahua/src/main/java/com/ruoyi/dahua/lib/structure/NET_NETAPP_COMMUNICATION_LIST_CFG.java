package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 通讯录配置
 * @date 2023/03/15 21:15:01
 */
public class NET_NETAPP_COMMUNICATION_LIST_CFG extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 实际的通讯录配置个数
	 */
    public int              nConmmunicationListNum;
	/**
	 * 通讯录配置数组
	 */
    public NET_NETAPP_COMMUNICATION_LIST[] stuCommunication = new NET_NETAPP_COMMUNICATION_LIST[10];

	public NET_NETAPP_COMMUNICATION_LIST_CFG() {
		for (int i = 0; i < stuCommunication.length; i++) {
			stuCommunication[i] = new NET_NETAPP_COMMUNICATION_LIST();
		}
		this.dwSize = this.size();
	}
}

