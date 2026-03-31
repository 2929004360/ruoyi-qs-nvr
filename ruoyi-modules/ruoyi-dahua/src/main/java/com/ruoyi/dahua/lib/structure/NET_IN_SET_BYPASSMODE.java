package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 设置旁路状态
 *              输入参数。此时CLIENT_SetAlarmRegionInfo的emType参数为NET_EM_SET_ALARMREGION_INFO_BYPASSMODE
 * @date 2023/03/15 16:33:42
 */
public class NET_IN_SET_BYPASSMODE extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 密码
	 */
    public byte[]           szPwd = new byte[256];
	/**
	 * 旁路模式 {@link com.ruoyi.dahua.lib.enumeration.EM_BYPASSMODE_TYPE}
	 */
    public int              emType;
	/**
	 * 防区个数
	 */
    public int              nZoneNum;
	/**
	 * 防区号
	 */
    public int[]            arrZones = new int[72];
	/**
	 * 防区个数扩展
	 */
    public int              nZoneNumEx;
	/**
	 * 防区号扩展，超过72时使用此字段
	 */
    public int[]            arrZonesEx = new int[256];

	public NET_IN_SET_BYPASSMODE() {
		this.dwSize = this.size();
	}
}

