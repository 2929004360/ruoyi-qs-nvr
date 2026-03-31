package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_QueryNewSystemInfo 接口的命令 CFG_CAP_ALARM (获取报警能力集)对应结构体
 *              传感器报警方式
 * @date 2023/04/18 10:57:31
 */
public class CFG_ALARM_SENSE_METHOD extends NetSDKLib.SdkStructure {
	/**
	 * 支持的传感器方式数
	 */
    public int              nSupportSenseMethodNum;
	/**
	 * 支持的传感器方式，参考枚举{ @link EM_SENSE_METHOD}
	 */
    public int[]            emSupportSenseMethod = new int[16];

	public CFG_ALARM_SENSE_METHOD() {
	}
}

