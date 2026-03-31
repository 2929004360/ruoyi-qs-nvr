package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 补光灯灵敏度配置
 * @date 2023/05/23 09:40:10
 */
public class CFG_LIGHTING_V2_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 通道
	 */
    public int              nChannel;
	/**
	 * 白天黑夜对应灯光配置数量
	 */
    public int              nDNLightInfoNum;
	/**
	 * 白天黑夜对应灯光配置 从元素0开始分别表示 白天、夜晚、普通、顺光、一般逆光、强逆光、低照度、自定义
	 */
    public CFG_LIGHTING_V2_DAYNIGHT[] anDNLightInfo = new CFG_LIGHTING_V2_DAYNIGHT[8];

	public CFG_LIGHTING_V2_INFO() {
		for (int i = 0; i < anDNLightInfo.length; i++) {
			anDNLightInfo[i] = new CFG_LIGHTING_V2_DAYNIGHT();
		}
	}
}

