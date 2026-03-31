package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 设置布防模式
 *              输入参数。此时CLIENT_SetAlarmRegionInfo的emType参数为NET_EM_SET_ALARMREGION_INFO_ARMMODE
 * @date 2023/03/15 16:05:04
 */
public class NET_IN_SET_ALARMMODE extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 布撤防类型 {@link com.ruoyi.dahua.lib.enumeration.EM_ARM_TYPE}
	 */
    public int              emArmType;
	/**
	 * 密码
	 */
    public byte[]           szPwd = new byte[256];
	/**
	 * 区域的个数
	 */
    public int              nAreaNum;
	/**
	 * 区域号
	 */
    public int[]            arrAreas = new int[8];
	/**
	 * 执行布撤防时的附加信息
	 */
    public NET_DETAIL_INFO  stuDetail = new NET_DETAIL_INFO();
	/**
	 * 区域的扩展个数
	 */
    public int              nAreaNumEx;
	/**
	 * 区域号扩展 个数超过8个使用这个字段
	 */
    public int[]            arrAreasEx = new int[64];

	public NET_IN_SET_ALARMMODE() {
		this.dwSize = this.size();
	}
}

