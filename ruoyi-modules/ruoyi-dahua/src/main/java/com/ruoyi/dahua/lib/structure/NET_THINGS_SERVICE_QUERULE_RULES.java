package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 规则详情
 * @date 2022/04/20 11:28:15
 */
public class NET_THINGS_SERVICE_QUERULE_RULES extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小, 调用者必须初始化该字段
	 */
    public int              dwSize;
	/**
	 * 使能
	 */
    public int              bEnable;
	/**
	 * 规则ID
	 */
    public byte[]           szRuleID = new byte[256];
	/**
	 * 联动规则内容
	 */
    public NET_THINGS_SERVICE_QUERULE_RULECONTENT stuRuleContent = new NET_THINGS_SERVICE_QUERULE_RULECONTENT();

	public NET_THINGS_SERVICE_QUERULE_RULES() {
		this.dwSize = this.size();
	}
}

