package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 溢出控制列表,对应配置项NET_EM_CFG_RTSC_SCH_OVERFLOW_CONTROL
 * @date 2021/09/28
 */
public class NET_CFG_RTSC_SCH_OVERFLOW_CONTROL_INFO extends NetSDKLib.SdkStructure {
	/**
     *  结构体大小 
     */
    public int              dwSize;
    /**
     *  溢出控制智能化配置列表个数
     */
    public int              nOverflowControlNum;
    /**
     *  溢出控制智能化配置列表
     */
    public NET_OVER_FLOW_CONTROL_INFO[] stuOverFlowControlInfo = (NET_OVER_FLOW_CONTROL_INFO[]) new NET_OVER_FLOW_CONTROL_INFO().toArray(20);

    public NET_CFG_RTSC_SCH_OVERFLOW_CONTROL_INFO(){
        this.dwSize = this.size();
    }
}

