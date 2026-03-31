package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 周期方案表, 对应配置项NET_EM_CFG_RTSC_SCH_PLANS
 * @date 2021/09/02
 */
public class NET_CFG_RTSC_SCH_PLANS_INFO extends NetSDKLib.SdkStructure {
	/**
     *  结构体大小
     */
    public int              dwSize;
    /**
     *  最大方案个数 只读
     */
    public int              nMaxCyclePlans;
    /**
     *  周期方案列表个数
     */
    public int              nCyclePalnNum;
    /**
     *  周期方案列表
     */
    public NET_CYCLE_PLAN_INFO[] stuCyclePlan = (NET_CYCLE_PLAN_INFO[]) new NET_CYCLE_PLAN_INFO().toArray(20);

    public NET_CFG_RTSC_SCH_PLANS_INFO(){
        this.dwSize = this.size();
    }
}

