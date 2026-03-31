package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description VK二值对信息
 * @date 2022/11/03 09:43:17
 */
public class NET_VKINFO extends NetSDKLib.SdkStructure {
	/**
	 * VKID
	 */
    public byte[]           szVKID = new byte[128];
	/**
	 * VK
	 */
    public byte[]           szVK = new byte[128];
	/**
	 * 加密算法类型 {@link com.ruoyi.dahua.lib.enumeration.EM_ALGORITHM_TYPE}
	 */
    public int              emAlgorithmType;
	/**
	 * 返回的VKID字段的实际大小
	 */
    public int              nRetVKIDLen;
	/**
	 * 返回的VK实际大小
	 */
    public int              nRetVKLen;
	/**
	 * 是否加密 {@link com.ruoyi.dahua.lib.enumeration.EM_IS_ENCRYPT}
	 */
    public int              emIsEncrypt;
	/**
	 * 是否是当前VK 0 ：未知，1：当前， 2：之前 {@link com.ruoyi.dahua.lib.enumeration.EM_IS_CURRENT_VK}
	 */
    public int              emIsCurrent;
	/**
	 * 保留字节
	 */
    public byte[]           byReserved = new byte[492];

	public NET_VKINFO() {
	}
}

