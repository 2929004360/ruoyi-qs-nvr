package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 时间
 * @date 2022/09/08 19:33:10
 */
public class CFG_NET_TIME_EX extends NetSDKLib.SdkStructure {
	/**
	 * 年
	 */
    public int              dwYear;
	/**
	 * 月
	 */
    public int              dwMonth;
	/**
	 * 日
	 */
    public int              dwDay;
	/**
	 * 时
	 */
    public int              dwHour;
	/**
	 * 分
	 */
    public int              dwMinute;
	/**
	 * 秒
	 */
    public int              dwSecond;
	/**
	 * 毫秒
	 */
    public int              dwMillisecond;
	/**
	 * 保留字段
	 */
    public int[]            dwReserved = new int[2];

	public CFG_NET_TIME_EX() {
	}

	public String toStringTime() {
		return String.format("%02d/%02d/%02d %02d:%02d:%02d.%02d", dwYear, dwMonth, dwDay, dwHour, dwMinute, dwSecond,dwMillisecond);
	}
}

