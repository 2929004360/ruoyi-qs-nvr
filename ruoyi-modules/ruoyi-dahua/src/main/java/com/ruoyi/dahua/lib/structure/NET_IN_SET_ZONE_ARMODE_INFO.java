package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description CLIENT_SetZoneArmMode 接口入参
 * @origin autoTool
 * @date 2023/08/10 09:52:28
 */
public class NET_IN_SET_ZONE_ARMODE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 此结构体大小,必须赋值
	 */
    public int              dwSize;
	/**
	 * 要操作的防区号列表个数
	 */
    public int              nZoneNum;
	/**
	 * 要操作的防区号列表，从1开始。
	 */
    public int[]            nZones = new int[256];
	/**
	 * 密码明文
	 */
    public byte[]           szPwd = new byte[64];
	/**
	 * 布撤防模式:1."T":Total布防/外出布防模式 2."D" 撤防
	 */
    public byte[]           szArmMode = new byte[32];
	/**
	 * 布撤防详细信息
	 */
    public NET_ARM_DETAIL_OPTIONS stuArmDetailOptions = new NET_ARM_DETAIL_OPTIONS();

	public NET_IN_SET_ZONE_ARMODE_INFO() {
		this.dwSize = this.size();
	}
}

