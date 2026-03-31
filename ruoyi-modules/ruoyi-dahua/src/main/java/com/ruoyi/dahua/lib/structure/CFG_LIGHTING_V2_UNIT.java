package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 补光灯灵敏度配置信息单元
 * @date 2023/05/23 09:40:10
 */
public class CFG_LIGHTING_V2_UNIT extends NetSDKLib.SdkStructure {
	/**
	 * 灯光类型 {@link com.ruoyi.dahua.lib.enumeration.EM_CFG_LC_LIGHT_TYPE}
	 */
    public int              emLightType;
	/**
	 * 灯光模式 {@link com.ruoyi.dahua.lib.enumeration.EM_CFG_LC_MODE}
	 */
    public int              emMode;
	/**
	 * 灯光补偿
	 */
    public int              nCorrection;
	/**
	 * 灯光灵敏度
	 */
    public int              nSensitive;
	/**
	 * 补光灯开关延时
	 */
    public int              nLightSwitchDelay;
	/**
	 * 近光灯组信息
	 */
    public NET_LIGHT_INFO[] anNearLight = new NET_LIGHT_INFO[4];
	/**
	 * 近光灯组数量
	 */
    public int              nNearLightLen;
	/**
	 * 中光灯组信息
	 */
    public NET_LIGHT_INFO[] anMiddleLight = new NET_LIGHT_INFO[4];
	/**
	 * 中光灯组数量
	 */
    public int              nMiddleLightLen;
	/**
	 * 远光灯组信息
	 */
    public NET_LIGHT_INFO[] anFarLight = new NET_LIGHT_INFO[4];
	/**
	 * 远光灯组数量
	 */
    public int              nFarLightLen;
	/**
	 * 当前白光灯上限亮度相对于白光灯最大亮度的百分比0~100
	 */
    public int              nPercentOfMaxBrightness;
	/**
	 * 智能补光方案下生效，表示红外白光切换延时，防止来回切换影响寿命和体验单位秒 范围0-300 默认30s
	 */
    public int              nAIMixLightSwitchDelay;
	/**
	 * 预留字节
	 */
    public byte[]           byReserved = new byte[120];

	public CFG_LIGHTING_V2_UNIT() {
		for (int i = 0; i < anNearLight.length; i++) {
			anNearLight[i] = new NET_LIGHT_INFO();
		}
		for (int i = 0; i < anMiddleLight.length; i++) {
			anMiddleLight[i] = new NET_LIGHT_INFO();
		}
		for (int i = 0; i < anFarLight.length; i++) {
			anFarLight[i] = new NET_LIGHT_INFO();
		}
	}
}

