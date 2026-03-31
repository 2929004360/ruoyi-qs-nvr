package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 隐私遮挡通用能力集
 * @date 2022/07/21 13:40:57
 */
public class NET_PRIVACY_MASKING_CAPS extends NetSDKLib.SdkStructure {
	/**
	 * 是否支持PrivacyMasking设置
	 */
    public int              bSupport;
	/**
	 * 隐私遮档版本号 {@link com.ruoyi.dahua.lib.enumeration.EM_PRIVACY_MASKING_VERSION}
	 */
    public int              emVersion;
	/**
	 * 支持最大隐私遮挡块数目
	 */
    public int              nSupportMaskingNum;
	/**
	 * 系统支持的隐私遮挡块（包括纯色块和马赛克）最大个数,代表了芯片平台能支持的最大能力。
	 */
    public int              nMaxChipMaskingNum;
	/**
	 * 纯色隐私遮挡块的能力集
	 */
    public NET_COLOR_MASKING_CAPS stuColorMaskingCaps = new NET_COLOR_MASKING_CAPS();
	/**
	 * 马赛克隐私遮挡块的能力集
	 */
    public NET_MOSAIC_MASKING_CAPS stuMosaicMaskingCaps = new NET_MOSAIC_MASKING_CAPS();
	/**
	 * 屏蔽倍率能力集
	 */
    public NET_SHIELD_ZOOM_CAPS stuShieldZoom = new NET_SHIELD_ZOOM_CAPS();
	/**
	 * 保留字节
	 */
    public byte[]           byReserved = new byte[1024];

	public NET_PRIVACY_MASKING_CAPS() {
	}
}

