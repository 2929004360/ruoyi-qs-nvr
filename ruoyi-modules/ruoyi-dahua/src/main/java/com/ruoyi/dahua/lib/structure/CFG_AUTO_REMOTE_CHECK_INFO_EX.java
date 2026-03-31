package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 开门远程验证扩展
 * @origin autoTool
 * @date 2023/08/24 14:01:18
 */
public class CFG_AUTO_REMOTE_CHECK_INFO_EX extends NetSDKLib.SdkStructure {
	/**
	 * 远程验证假日计划, 需要远程验证的假日计划, 值为AccessHolidaySchedule索引
	 */
    public int              nHolidayScheduleID;
	/**
	 * 保留字节
	 */
    public byte[]           szResvered = new byte[60];

	public CFG_AUTO_REMOTE_CHECK_INFO_EX() {
	}
}

