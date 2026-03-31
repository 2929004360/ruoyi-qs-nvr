package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 报警产品本地用户扩展信息
 * @date 2023/03/16 10:46:38
 */
public class NET_ALARM_USER_EXTERN extends NetSDKLib.SdkStructure {
	/**
	 * 胁迫密码
	 */
    public byte[]           szDuressPassword = new byte[64];
	/**
	 * 关联卡片
	 */
    public StringByteArr32[] szCard = new StringByteArr32[64];
	/**
	 * 关联卡片数量
	 */
    public int              nCardNum;
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[1020];

	public NET_ALARM_USER_EXTERN() {
		for (int i = 0; i < szCard.length; i++) {
			szCard[i] = new StringByteArr32();
		}
	}
}

