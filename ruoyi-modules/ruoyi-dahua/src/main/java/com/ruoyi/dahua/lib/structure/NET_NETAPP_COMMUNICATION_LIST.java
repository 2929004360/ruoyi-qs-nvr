package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 通讯录配置列表
 * @date 2023/03/15 21:15:01
 */
public class NET_NETAPP_COMMUNICATION_LIST extends NetSDKLib.SdkStructure {
	/**
	 * 自定义组名,不可重复命名
	 */
    public byte[]           szGroupName = new byte[16];
	/**
	 * 联系人个数
	 */
    public int              nMembersNum;
	/**
	 * 联系人信息
	 */
    public NET_MEMBERS_INFO[] arrMembers = new NET_MEMBERS_INFO[32];
	/**
	 * 用户组关联通知信息
	 */
    public NET_NOTIFY_INFO  stuNotifyInfo = new NET_NOTIFY_INFO();

	public NET_NETAPP_COMMUNICATION_LIST() {
		for (int i = 0; i < arrMembers.length; i++) {
			arrMembers[i] = new NET_MEMBERS_INFO();
		}
	}
}

