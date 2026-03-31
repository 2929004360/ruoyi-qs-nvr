package com.ruoyi.dahua.lib.structure.optimized;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 灯组灯色信息
 * @date 2022/11/28 09:35:44
 */
public class LAMP_GROUP_INFO_V1 extends NetSDKLib.SdkStructure {
	/**
	 * 灯组编号
	 */
	public int nLampNo;
	/**
	 * 灯组类型 {@link com.ruoyi.dahua.lib.enumeration.EM_LAMP_TYPE}
	 */
	public int emLampType;
	/**
	 * 信号灯组灯色剩余时间，整数，单位为秒（s）
	 */
	public int nRemainTime;
	/**
	 * 灯组灯色个数
	 */
	public int nLampColorCount;
	/**
	 * 灯组灯色当灯组类型为1~12时,int[0]表示红色发光单元，int[1]表示黄色发光单元，int[2]表示绿色发光单元，int[3]保留
	 * 当灯组类型为13~15时，int[0]用于表示禁止通行信号发光单元，int[1]用于表示过渡信号发光单元，int[2]用于表示通行信号发光单元，int[3]保留
	 * 具体取值 0：无灯 1：灭灯 2：亮灯3：闪烁
	 */
	public int[] nLampColor = new int[4];

	public LAMP_GROUP_INFO_V1() {
	}
}