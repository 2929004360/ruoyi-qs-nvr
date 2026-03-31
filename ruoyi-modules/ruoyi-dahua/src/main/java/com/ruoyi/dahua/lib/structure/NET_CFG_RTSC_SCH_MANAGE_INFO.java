package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 红绿灯调度计划列表，对应配置项NET_EM_CFG_RTSC_SCH_MANAGE
 * @date 2021/09/14
 */
public class NET_CFG_RTSC_SCH_MANAGE_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  结构体大小
     */
    public int              dwSize;
    /**
     *  最大日方案数
     */
    public int              nMaxDayPlans;
    /**
     *  最大时段数
     */
    public int              nMaxDayActions;
    /**
     *  最大周计划数
     */
    public int              nMaxWeekPlans;
    /**
     *  最大日常调度方案个数
     */
    public int              nMaxCommonDatePlans;
    /**
     *  最大特殊调度方案个数
     */
    public int              nMaxSpecialDatePlans;
    /**
     *  日计划方案个数
     */
    public int              nDayplansNum;
    /**
     *  日计划方案  
     */
    public NET_DAY_PLAN_INFO[] stuDayPlanInfo = (NET_DAY_PLAN_INFO[]) new NET_DAY_PLAN_INFO().toArray(56);
    /**
     *  周计划列表个数
     */
    public int              nWeekPlansNum;
    /**
     *  周计划列表
     */
    public NET_WEEKS_PLAN_INFO[] stuWeeksPlanInfo = (NET_WEEKS_PLAN_INFO[]) new NET_WEEKS_PLAN_INFO().toArray(20);
    /**
     *  日常调度列表个数
     */
    public int              nCommonDatePlanNum;
    /**
     *  日常调度列表
     */
    public NET_COMMON_DATE_PLAN_INFO[] stuCommonDatePlanInfo = (NET_COMMON_DATE_PLAN_INFO[]) new NET_COMMON_DATE_PLAN_INFO().toArray(16);
    /**
     *  特殊调度列表个数
     */
    public int              nSpecialDateNum;
    /**
     *  特殊调度列表
     */
    public NET_SPECIAL_DATE_PLAN_INFO[] stuSpecialDatePlan = (NET_SPECIAL_DATE_PLAN_INFO[]) new NET_SPECIAL_DATE_PLAN_INFO().toArray(16);

    public NET_CFG_RTSC_SCH_MANAGE_INFO(){
        this.dwSize = this.size();
    }
}

