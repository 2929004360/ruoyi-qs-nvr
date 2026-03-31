package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 雷达屏幕显示规则配置, 对应配置项NET_EM_CFG_RADAR_SCREEN_RULE
 * @date 2022/06/13 19:27:38
 */
public class NET_CFG_RADAR_SCREEN_RULE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 雷达屏幕显示规则个数, stuScreenRule 数组的有效元素个数
	 */
    public int              nScreenRuleNum;
	/**
	 * 雷达屏幕显示规则
	 */
    public NET_RADAR_SCREEN_RULE[] stuScreenRule = new NET_RADAR_SCREEN_RULE[10];

	public NET_CFG_RADAR_SCREEN_RULE_INFO() {
		for (int i = 0; i < stuScreenRule.length; i++) {
			stuScreenRule[i] = new NET_RADAR_SCREEN_RULE();
		}
		this.dwSize = this.size();
	}
}

