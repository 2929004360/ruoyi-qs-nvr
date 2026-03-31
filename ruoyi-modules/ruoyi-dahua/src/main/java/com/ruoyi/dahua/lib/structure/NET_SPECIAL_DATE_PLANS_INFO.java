package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 特殊调度列表信息
 * @date 2021/09/28
 */
public class NET_SPECIAL_DATE_PLANS_INFO extends NetSDKLib.SdkStructure {
	/**
     *  日方案编号，从1开始
     */
    public int              nDatePlanNumber;
    /**
     *  日期开始月份
     */
    public int              nBeginMonth;
    /**
     *  日期开始日
     */
    public int              nBeginDay;
    /**
     *  日期结束月份
     */
    public int              nEndMonth;
    /**
     *  日期结束日
     */
    public int              nEndDay;
    /**
     *  周计划编号
     */
    public int              nWeekPlanNumber;
    /**
     *  周计划名称
     */
    public byte[]           szWeekPlanName = new byte[64];
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[32];
}

