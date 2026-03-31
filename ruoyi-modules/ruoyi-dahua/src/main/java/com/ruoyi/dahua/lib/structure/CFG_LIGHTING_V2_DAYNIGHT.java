package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 白天黑夜补光灯灵敏度配置
 * @date 2023/05/23 09:40:10
 */
public class CFG_LIGHTING_V2_DAYNIGHT extends NetSDKLib.SdkStructure {
	/**
	 * 各类型灯光信息
	 */
    public CFG_LIGHTING_V2_UNIT[] anLightInfo = new CFG_LIGHTING_V2_UNIT[3];
	/**
	 * 灯光类型数量
	 */
    public int              nLightInfoLen;

	public CFG_LIGHTING_V2_DAYNIGHT() {
		for (int i = 0; i < anLightInfo.length; i++) {
			anLightInfo[i] = new CFG_LIGHTING_V2_UNIT();
		}
	}
}

