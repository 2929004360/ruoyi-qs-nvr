package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 眼镜相关属性状态信息
 * @date 2022/11/01 19:47:17
 */
public class NET_GLASSES_RELATED_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 眼镜检测规则中报警类型 {@link com.ruoyi.dahua.lib.enumeration.EM_GLASSES_RULE_TYPE}
	 */
    public int              emGlassesType;
	/**
	 * 眼镜检测结果, 0-合规 1-不合规 2-未知
	 */
    public int              nGlassesLegalMask;
	/**
	 * 预留字节
	 */
    public byte[]           szReserved = new byte[64];

	public NET_GLASSES_RELATED_INFO() {
	}
}

