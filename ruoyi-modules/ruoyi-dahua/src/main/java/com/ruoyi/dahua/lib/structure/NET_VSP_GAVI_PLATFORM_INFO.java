package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib.SdkStructure;
import com.ruoyi.dahua.lib.enumeration.EM_VSP_GAVI_PLATFORM_CODE;
/**
 * @author 119178
 * @description 接入平台范围信息
 * @date 2021/4/19
 */
public class NET_VSP_GAVI_PLATFORM_INFO extends SdkStructure {
	/**
	 * {@link EM_VSP_GAVI_PLATFORM_CODE}
	 * 平台码
	 */
    public int              emPlatformCode;
	/**
	 * 平台名称
	 */
    public byte[]           szPlatformName = new byte[128];
	/**
	 * 预留字段
	 */
    public byte[]           byReserved = new byte[132];
}

