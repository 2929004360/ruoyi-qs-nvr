package com.ruoyi.dahua.lib.structure.optimized;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 灯色状态信息
 * @date 2022/11/28 09:35:44
 */
public class LAMP_STATE_V1 extends NetSDKLib.SdkStructure {
	/**
	 * 信号灯控制路口的进口数量（0~8）
	 */
	public int nEnterNumber;
	/**
	 * 进口灯色状态信息个数
	 */
	public int nEnterInfoNum;
	/**
	 * 进口灯色状态信息，包含1到N（路口进口数量）个进口灯色状态信息
	 */
	public ENTER_INFO_V1[] stuEnterInfo = new ENTER_INFO_V1[8];

	public LAMP_STATE_V1() {
		for (int i = 0; i < stuEnterInfo.length; i++) {
			stuEnterInfo[i] = new ENTER_INFO_V1();
		}
	}
}