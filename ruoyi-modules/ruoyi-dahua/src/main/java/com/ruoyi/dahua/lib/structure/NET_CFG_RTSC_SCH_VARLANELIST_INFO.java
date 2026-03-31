package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 可变车道列表，对应配置项NET_EM_CFG_RTSC_SCH_VARLANELIST
 * @date 2021/09/28
 */
public class NET_CFG_RTSC_SCH_VARLANELIST_INFO extends NetSDKLib.SdkStructure {
	/**
     *  结构体大小
     */
    public int              dwSize;
    /**
     *  可变车道列表个数
     */
    public int              nVarlaneListNum;
    /**
     *  可变车道列表
     */
    public NET_VAR_LANE_LIST_INFO[] stuVarLaneListInfo = (NET_VAR_LANE_LIST_INFO[]) new NET_VAR_LANE_LIST_INFO().toArray(8);
    /**
     *  可变车道计划表个数
     */
    public int              nVarlanePlanListNum;
    /**
     *  可变车道计划表
     */
    public NET_VARLANE_PLAN_LIST_INFO[] stuVarlanePlanInfo = (NET_VARLANE_PLAN_LIST_INFO[]) new NET_VARLANE_PLAN_LIST_INFO().toArray(8);

    public NET_CFG_RTSC_SCH_VARLANELIST_INFO(){
        this.dwSize = this.size();
    }
}

