package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * @author 251823
 * @description  核酸检测信息
 * @date 2021/08/15
 */
public class NET_HSJC_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  核酸检测报告日期 (yyyy-MM-dd)
     */
    public byte[]           szHSJCReportDate = new byte[32];
    /**
     *  核酸检测报告有效期(天)
     */
    public int              nHSJCExpiresIn;
    /**
     *  核酸检测报告结果
     */
    public int              nHSJCResult;
    /**
     * 核酸检测机构
    */
    public byte[]           szHSJCInstitution = new byte[256];
    /**
     *  保留字节
     */
    public byte[]           szReserved = new byte[768];
}

