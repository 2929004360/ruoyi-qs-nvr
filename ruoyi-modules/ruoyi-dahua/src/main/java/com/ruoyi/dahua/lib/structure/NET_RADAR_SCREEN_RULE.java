package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 雷达屏幕显示规则
 * @date 2022/06/13 19:27:38
 */
public class NET_RADAR_SCREEN_RULE extends NetSDKLib.SdkStructure {
	/**
	 * 屏幕显示的规则名字
	 */
    public byte[]           szRuleName = new byte[512];
	/**
	 * 屏幕文字颜色，0：红色，1：绿色，2：黄色
	 */
    public int              nDisplayColor;
	/**
	 * 屏幕文字显示方式，0：立即显示；1：滚动显示
	 */
    public int              nDisplayMode;
	/**
	 * 屏幕文字滚动速度，当显示方式为立即显示时速度为0，速度范围在0-9
	 */
    public int              nDisplaySpeed;
	/**
	 * 屏幕显示的文本内容
	 */
    public byte[]           szDisplayText = new byte[512];
	/**
	 * 屏幕显示的规则编号
	 */
    public int              nRuleID;
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[1024];

	public NET_RADAR_SCREEN_RULE() {
	}
}

