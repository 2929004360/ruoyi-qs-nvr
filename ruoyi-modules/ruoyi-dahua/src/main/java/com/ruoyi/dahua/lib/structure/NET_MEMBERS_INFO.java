package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 联系人信息
 * @date 2023/03/15 21:15:00
 */
public class NET_MEMBERS_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 联系人姓名
	 */
    public byte[]           szName = new byte[32];
	/**
	 * 设备描述
	 */
    public byte[]           szMobile = new byte[16];
	/**
	 * 电子邮箱
	 */
    public byte[]           szEmail = new byte[32];
	/**
	 * 组织
	 */
    public byte[]           szOrganization = new byte[64];

	public NET_MEMBERS_INFO() {
	}
}

