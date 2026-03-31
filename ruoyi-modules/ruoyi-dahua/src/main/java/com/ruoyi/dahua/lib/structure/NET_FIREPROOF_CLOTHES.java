package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 防火衣相关属性状态信息
 * @date 2022/11/01 19:47:04
 */
public class NET_FIREPROOF_CLOTHES extends NetSDKLib.SdkStructure {
	/**
	 * 是否穿着防火衣 {@link com.ruoyi.dahua.lib.enumeration.EM_FIREPROOF_CLOTHES_STATE}
	 */
    public int              emHasFireProofClothes;
	/**
	 * 防火衣颜色 {@link com.ruoyi.dahua.lib.enumeration.EM_CLOTHES_COLOR}
	 */
    public int              emFireProofClothesColor;
	/**
	 * 预留字节
	 */
    public byte[]           szReserved = new byte[128];

	public NET_FIREPROOF_CLOTHES() {
	}
}

