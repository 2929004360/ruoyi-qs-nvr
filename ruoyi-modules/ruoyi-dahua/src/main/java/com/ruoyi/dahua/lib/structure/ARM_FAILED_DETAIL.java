package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 布防失败细节
 * @date 2023/03/15 16:25:59
 */
public class ARM_FAILED_DETAIL extends NetSDKLib.SdkStructure {
	/**
	 * 布防失败的区域号
	 */
    public int              nArea;
	/**
	 * 报警源输入返回个数
	 */
    public int              nAlarmSourceRet;
	/**
	 * 区域下包含的有源输入的防区
	 */
    public int[]            arrAlarmSource = new int[72];
	/**
	 * 保留字节
	 */
    public byte[]           byReserved = new byte[1024];

	public ARM_FAILED_DETAIL() {
	}
}

