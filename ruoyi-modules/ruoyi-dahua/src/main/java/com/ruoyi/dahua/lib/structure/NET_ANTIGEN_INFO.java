package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 抗原检测信息
 * @date 2022/05/21 15:04:32
 */
public class NET_ANTIGEN_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 抗原检测报告日期
	 */
    public byte[]           szAntigenReportDate = new byte[32];
	/**
	 * 抗原检测报告结果
	 */
    public int              nAntigenStatus;
	/**
	 * 抗原检测报告有效期(单位:天)
	 */
    public int              nAntigenExpiresIn;
	/**
	 * 保留字节
	 */
    public byte[]           szResvered = new byte[256];

	public NET_ANTIGEN_INFO() {
	}
}

