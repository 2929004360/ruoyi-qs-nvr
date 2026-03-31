package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description FireWarningExt 配置( 对应CFG_CMD_FIRE_WARNING_EXT)
 * @date 2023/05/24 10:24:47
 */
public class CFG_FIREWARNING_EXT_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 可见光通道是否需要全景图
	 */
    public int              bVisualOverviewEnable;
	/**
	 * 可见光通道是否需要细节图
	 */
    public int              bVisualDetailEnable;
	/**
	 * 热成像通道抓图张数
	 */
    public int              nThermoSnapTimes;

	public CFG_FIREWARNING_EXT_INFO() {
	}
}

