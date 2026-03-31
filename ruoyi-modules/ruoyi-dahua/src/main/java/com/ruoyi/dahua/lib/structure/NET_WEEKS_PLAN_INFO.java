package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 周计划列表信息
 * @date 2021/09/27
 */
public class NET_WEEKS_PLAN_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  周计划编号
     */
    public int              nWeekPlanNumber;
    /**
     *  周计划个数
     */
    public int              nWeekPlanNum;
    /**
     *  周计划名称
     */
    public byte[]           szWeekPlanName = new byte[32];
    /**
     *  周计划
     */
    public NET_WEEK_PLAN_INFO[] stuWeekPlanInfo = (NET_WEEK_PLAN_INFO[]) new NET_WEEK_PLAN_INFO().toArray(7);
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[32];
}

