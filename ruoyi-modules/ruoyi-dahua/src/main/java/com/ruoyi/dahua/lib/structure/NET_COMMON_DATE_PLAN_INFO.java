package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 日常调度列表信息
 * @date 2021/09/27
 */
public class NET_COMMON_DATE_PLAN_INFO extends NetSDKLib.SdkStructure {
	  /**
     *  日常调度编号
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
     *  周调度编号
     */
    public int              nWeekPlanNumber;
    /**
     *  周调度名称
     */
    public byte[]           szWeekPlanName = new byte[64];
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[32];
}

