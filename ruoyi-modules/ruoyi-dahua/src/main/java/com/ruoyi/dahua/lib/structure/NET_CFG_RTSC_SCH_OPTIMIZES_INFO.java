package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 饱和度与单点自适应智能化配置，对应配置项NET_EM_CFG_RTSC_SCH_OPTIMIZES
 * @date 2021/09/28
 */
public class NET_CFG_RTSC_SCH_OPTIMIZES_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  结构体大小 
     */
    public int              dwSize;
    /**
     *  能度与单点完成配置表个数
     */
    public int              nOptimizeInfoNum;
    /**
     *  能度与单点完成配置表
     */
    public NET_OPTIMIZES_INFO[] stuOptimizeInfo = (NET_OPTIMIZES_INFO[]) new NET_OPTIMIZES_INFO().toArray(20);

    public NET_CFG_RTSC_SCH_OPTIMIZES_INFO(){
        this.dwSize = this.size();
    }
}

