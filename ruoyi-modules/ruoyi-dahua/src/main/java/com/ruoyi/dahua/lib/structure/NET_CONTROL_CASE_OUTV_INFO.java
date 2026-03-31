package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 输出电压控制开关
 * @date 2023/05/11 13:59:24
 */
public class NET_CONTROL_CASE_OUTV_INFO extends NetSDKLib.SdkStructure {
	/**
	 * DC12v1电压开关
	 */
    public int              bDc12v1;
	/**
	 * DC12v2电压开关
	 */
    public int              bDc12v2;
	/**
	 * Ac24v1电压开关
	 */
    public int              bAc24v1;
	/**
	 * Ac24v2电压开关
	 */
    public int              bAc24v2;
	/**
	 * Ac220v1电压开关
	 */
    public int              bAc220v1;
	/**
	 * Ac220v2电压开关
	 */
    public int              bAc220v2;
	/**
	 * Ac220v3电压开关
	 */
    public int              bAc220v3;
	/**
	 * 字节对齐
	 */
    public byte[]           szReserved1 = new byte[4];
	/**
	 * AC220v1 输出别名设置
	 */
    public byte[]           szAc220v1Title = new byte[64];
	/**
	 * AC220v2 输出别名设置
	 */
    public byte[]           szAc220v2Title = new byte[64];
	/**
	 * AC220v3 输出别名设置
	 */
    public byte[]           szAc220v3Title = new byte[64];
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[256];

	public NET_CONTROL_CASE_OUTV_INFO() {
	}
}

