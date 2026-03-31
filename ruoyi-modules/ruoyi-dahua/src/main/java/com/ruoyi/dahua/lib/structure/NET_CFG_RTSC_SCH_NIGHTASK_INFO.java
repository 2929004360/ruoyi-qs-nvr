package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 夜间请求，对应配置项NET_EM_CFG_RTSC_SCH_NIGHTASK
 * @date 2021/09/28
 */
public class NET_CFG_RTSC_SCH_NIGHTASK_INFO extends NetSDKLib.SdkStructure {
	/**
     *  结构体大小
     */
    public int              dwSize;
    /**
     *  夜间请求智能化配置列表个数
     */
    public int              nNightAskNum;
    /**
     *  夜间请求智能化配置列表
     */
    public NET_NIGHT_ASK_INFO[] stuNightAskInfo = (NET_NIGHT_ASK_INFO[]) new NET_NIGHT_ASK_INFO().toArray(20);

    public NET_CFG_RTSC_SCH_NIGHTASK_INFO(){
        this.dwSize = this.size();
    }
}

