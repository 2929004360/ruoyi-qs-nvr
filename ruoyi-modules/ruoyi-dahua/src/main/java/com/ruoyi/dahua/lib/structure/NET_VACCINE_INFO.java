package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * @author 251823
 * @description  新冠疫苗接种信息
 * @date 2021/08/15
 */
public class NET_VACCINE_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  是否已接种新冠疫苗, 0: 否, 1: 是
     */
    public int              nVaccinateFlag;
    /**
     *  新冠疫苗名称
     */
    public byte[]           szVaccineName = new byte[128];
    /**
     *  历史接种日期有效个数
     */
    public int              nDateCount;
    /**
     *  历史接种日期 (yyyy-MM-dd). 如提供不了时间, 则填"0000-00-00", 表示已接种
     */
    public VaccinateDateByteArr[] szVaccinateDate = (VaccinateDateByteArr[])new VaccinateDateByteArr().toArray(8);
    /**
     * 是否已接种新冠疫苗加强针, 0: 未知, 1:否  2: 是
    */
    public int              nVaccineIntensifyFlag;
    /**
     *  保留字节
     */
    public byte[]           szReserved = new byte[1020];
}

