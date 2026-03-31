package com.ruoyi.dahua.lib.structure.optimized;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 进口灯色状态信息
 * @date 2022/11/28 09:35:44
 */
public class ENTER_INFO_V1 extends NetSDKLib.SdkStructure {
	/**
	 * 进口方向
	 */
	public int nEnterDir;
	/**
	 * 进口灯组数量（0~48）
	 */
	public int nLampNumber;
	/**
	 * 灯组灯色信息个数
	 */
	public int nLampGroupNum;
	/**
	 * 灯组灯色信息，包含1到M（进口灯组数量）个灯组灯色信息
	 */
	public LAMP_GROUP_INFO_V1[] stuLampGroupInfo = new LAMP_GROUP_INFO_V1[48];

	public ENTER_INFO_V1() {
		for (int i = 0; i < stuLampGroupInfo.length; i++) {
			stuLampGroupInfo[i] = new LAMP_GROUP_INFO_V1();
		}
	}
}