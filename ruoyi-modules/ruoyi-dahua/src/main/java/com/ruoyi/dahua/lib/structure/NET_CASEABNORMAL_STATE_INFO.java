package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 智能终端箱的异常状态信息
 * @date 2023/05/11 16:00:20
 */
public class NET_CASEABNORMAL_STATE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 水浸状态，00异常01正常
	 */
    public byte[]           szWater = new byte[32];
	/**
	 * 温湿度状态，00异常01正常
	 */
    public byte[]           szEnv = new byte[32];
	/**
	 * tf卡状态，00异常01正常
	 */
    public byte[]           szTf = new byte[32];
	/**
	 * 暴力开箱，00异常01正常
	 */
    public byte[]           szIllegalOpen = new byte[32];
	/**
	 * 交换机状态，00异常01正常
	 */
    public byte[]           szSwitchStatus = new byte[32];
	/**
	 * 风扇状态，00异常01正常
	 */
    public byte[]           szFan = new byte[32];
	/**
	 * 有线网络状态，00异常01正常
	 */
    public byte[]           szLan = new byte[32];
	/**
	 * 4G网络状态，00异常01正常
	 */
    public byte[]           szMobileNetwork = new byte[32];
	/**
	 * 输出电压状态
	 */
    public NET_CASEABNORMAL_OUTV_INFO stuOutV = new NET_CASEABNORMAL_OUTV_INFO();
	/**
	 * 输入电压状态对应取值及含义：过压：1 <<0欠压：1 <<1过流：1 <<2漏电：1 <<3断电：1<<4, 可以组合，比如过压+欠压：11
	 */
    public byte[]           szInV = new byte[32];
	/**
	 * 箱内相机状态，00异常01正常
	 */
    public byte[]           szIpcStatus = new byte[32];
	/**
	 * 智慧空开状态，长度最大32，对应取值及含义：过压：1<<0欠压：1<<1过流：1<<2漏电：1<<3断电：1<<4过温：1<<5打火：1<<6过载：1<<7,可以组合，比如过压+欠压：11
	 */
    public byte[]           szAirSwitch = new byte[32];
	/**
	 * 湿度状态，00异常01正常
	 */
    public byte[]           szIsWet = new byte[32];
	/**
	 * 光衰状态，00异常01正常
	 */
    public byte[]           szLightDecay = new byte[32];
	/**
	 * 交换机异常信息
	 */
    public NET_CASEABNORMAL_SWITCHWARN_INFO stuSwitchWarn = new NET_CASEABNORMAL_SWITCHWARN_INFO();
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[256];

	public NET_CASEABNORMAL_STATE_INFO() {
	}
}

