package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 可变车道调度计划列表，对应配置项NET_EM_CFG_RTSC_SCH_VARLANE_MANAGEMENT
 * @date 2021/09/28
 */
public class NET_WEEK_PLANS_INFO extends NetSDKLib.SdkStructure {
    /**
     *  周计划编号
     */
    public int              nWeekPlanNumber;
    /**
     *  周计划名称
     */
    public byte[]           szWeekPlanName = new byte[64];
    /**
     *  周计划个数
     */
    public int              nWeekPlanNum;
    /**
     *  周计划 
     */
    public NET_WEEK_PLAN_INFO[] stuWeekPlanInfo = (NET_WEEK_PLAN_INFO[]) new NET_WEEK_PLAN_INFO().toArray(7);
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[32];
}

