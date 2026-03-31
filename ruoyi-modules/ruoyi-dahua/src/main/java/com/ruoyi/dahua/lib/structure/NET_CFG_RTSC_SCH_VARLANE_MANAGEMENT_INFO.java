package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 可变车道调度计划列表，对应配置项NET_EM_CFG_RTSC_SCH_VARLANE_MANAGEMENT
 * @date 2021/09/28
 */
public class NET_CFG_RTSC_SCH_VARLANE_MANAGEMENT_INFO extends NetSDKLib.SdkStructure {
	/**
     *  结构体大小 
     */
    public int              dwSize;
    /**
     *  可变车道日方案列表个数
     */
    public int              nDayPlansNum;
    /**
     *  可变车道日方案列表
     */
    public NET_DAY_PLANS_INFO[] stuDayPlansInfo = (NET_DAY_PLANS_INFO[]) new NET_DAY_PLANS_INFO().toArray(20);
    /**
     *  周计划列表个数
     */
    public int              nWeekPlansNum;
    /**
     *  周计划列表
     */
    public NET_WEEK_PLANS_INFO[] stuWeekPlansInfo = (NET_WEEK_PLANS_INFO[]) new NET_WEEK_PLANS_INFO().toArray(10);
    /**
     *  日常调度列表个数
     */
    public int              nCommonDatePlanNum;
    /**
     *  日常调度列表 
     */
    public NET_COMMON_DATE_PLAN_INFO[] stuCommonDatePlanInfo = (NET_COMMON_DATE_PLAN_INFO[]) new NET_COMMON_DATE_PLAN_INFO().toArray(8);
    /**
     *  特殊调度列表个数
     */
    public int              nSpecialDatePlanNum;
    /**
     *  特殊调度列表
     */
    public NET_SPECIAL_DATE_PLANS_INFO[] stuSpecialDatePlanInfo = (NET_SPECIAL_DATE_PLANS_INFO[]) new NET_SPECIAL_DATE_PLANS_INFO().toArray(8);

    public NET_CFG_RTSC_SCH_VARLANE_MANAGEMENT_INFO(){
        this.dwSize = this.size();
    }
}

