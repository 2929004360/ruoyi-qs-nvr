package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 门禁提供客户的"系统设置"配置
 * @date 2022/06/02 10:15:21
 */
public class NET_CFG_CUSTOMER_DEV_SETTING_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 开门方式 {@link com.ruoyi.dahua.lib.enumeration.EM_CUSTOMER_DEV_SETTING_DOOR_METHOD}
	 */
    public int              emDoorMethod;
	/**
	 * 国密加密算法密钥：加密密钥的密文及校验码，字符串
	 */
    public byte[]           szEnckeycipher = new byte[1024];
    /**
     * 卡片去重时间(分钟)，在N分钟内，该卡只能刷卡成功1次。0表示不开启去重功能
    */
    public int              nFliterSameCard;

	public NET_CFG_CUSTOMER_DEV_SETTING_INFO() {
		this.dwSize = this.size();
	}
}

