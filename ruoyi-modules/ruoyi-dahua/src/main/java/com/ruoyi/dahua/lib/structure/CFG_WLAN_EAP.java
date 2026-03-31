package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 单个WLAN配置EAP
 * @date 2023/03/16 11:15:26
 */
public class CFG_WLAN_EAP extends NetSDKLib.SdkStructure {
	/**
	 * EAP方法 {@link com.ruoyi.dahua.lib.enumeration.EM_CFG_EAP_METHOD}
	 */
    public int              emMethod;
	/**
	 * EAP身份验证方法 {@link com.ruoyi.dahua.lib.enumeration.EM_CFG_EAP_AUTH_TYPE}
	 */
    public int              emAuthType;
	/**
	 * 身份
	 */
    public byte[]           szIdentity = new byte[64];
	/**
	 * 匿名身份
	 */
    public byte[]           szAnonymousID = new byte[64];
	/**
	 * 密码
	 */
    public byte[]           szPassword = new byte[64];
	/**
	 * CA证书
	 */
    public byte[]           szCaCert = new byte[512];
	/**
	 * 用户证书
	 */
    public byte[]           szUserCert = new byte[512];

	public CFG_WLAN_EAP() {
	}
}

