package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 行人优先智能化配置,对应配置项NET_EM_CFG_RTSC_SCH_PEDDESTRAIN_PRIORITY
 * @date 2021/09/27
 */
public class NET_CFG_RTSC_SCH_PEDDESTRAIN_PRIORITY_INFO extends NetSDKLib.SdkStructure {
	/**
     *  结构体大小
     */
    public int              dwSize;
    /**
     *  行人优先智能化配置列表个数
     */
    public int              nPedestrainPriorityNum;
    /**
     *  行人优先智能化配置列表
     */
    public NET_PEDESTRAIN_PRIORITY_INFO[] stupedestrainPriorityInfo = (NET_PEDESTRAIN_PRIORITY_INFO[]) new NET_PEDESTRAIN_PRIORITY_INFO().toArray(20);

    public NET_CFG_RTSC_SCH_PEDDESTRAIN_PRIORITY_INFO(){
        this.dwSize = this.size();
    }
}

