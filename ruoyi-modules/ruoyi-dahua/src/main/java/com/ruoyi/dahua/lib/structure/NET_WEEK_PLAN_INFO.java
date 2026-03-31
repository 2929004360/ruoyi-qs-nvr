package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 周计划信息
 * @date 2021/09/27
 */
public class NET_WEEK_PLAN_INFO extends NetSDKLib.SdkStructure {
	  /**
     *  星期类型| 0 星期日 | 1 星期一 | 2 星期二 | 3 星期三 | 4 星期四 | 5 星期五 | 6 星期六 |
     */
    public int              nWeekNumber;
    /**
     *  日计划编号，从1开始
     */
    public int              nDayPlanNumber;
    /**
     *  日计划名称
     */
    public byte[]           szDayPlanName = new byte[64];
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[32];
}

