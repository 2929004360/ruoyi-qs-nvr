package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 时间
 * @date 2022/12/19 09:10:21
 */
public class CFG_NET_TIME extends NetSDKLib.SdkStructure {
    public int              nStructSize;
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

	public CFG_NET_TIME() {
	}
}

