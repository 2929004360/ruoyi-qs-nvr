package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 一键布防配置
 * @date 2023/03/16 09:04:50
 */
public class NET_ALARM_USER_ONECLICKARMING extends NetSDKLib.SdkStructure {
	/**
	 * 一键布防使能
	 */
    public int              bEnable;
	/**
	 * 布防功能 {@link com.ruoyi.dahua.lib.enumeration.EM_ALARM_ONECLICKARMING_FUNCTION}
	 */
    public int              emFunction;
	/**
	 * 触发类型 {@link com.ruoyi.dahua.lib.enumeration.EM_ALARM_ONECLICKARMING_TRIGGEROPTION}
	 */
    public int              emTriggerOption;
	/**
	 * 一键布防类型 {@link com.ruoyi.dahua.lib.enumeration.NET_EM_SCENE_MODE}
	 */
    public int              emArmProfile;
	/**
	 * 布撤防模式 {@link com.ruoyi.dahua.lib.enumeration.EM_ARM_TYPE}
	 */
    public int              emArmMode;
	/**
	 * 保留字节
	 */
    public byte[]           byReserved = new byte[256];

	public NET_ALARM_USER_ONECLICKARMING() {
	}
}

