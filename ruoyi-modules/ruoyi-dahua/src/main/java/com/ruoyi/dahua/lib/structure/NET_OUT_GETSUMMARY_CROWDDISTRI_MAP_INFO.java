package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_GetSummaryCrowdDistriMap 接口输出参数
 * @date 2022/01/07
 */
public class NET_OUT_GETSUMMARY_CROWDDISTRI_MAP_INFO extends NetSDKLib.SdkStructure {
    /**
     *  结构体大小
     */
    public int              dwSize;
    /**
     *  检测区个数
     */
    public int              nCrowdStatNum;
    /**
     *  检测区信息
     */
    public NET_GETSUMMARY_CROWD_STAT_DATA[] stuCrowdStatData = (NET_GETSUMMARY_CROWD_STAT_DATA[]) new NET_GETSUMMARY_CROWD_STAT_DATA().toArray(8);

    public NET_OUT_GETSUMMARY_CROWDDISTRI_MAP_INFO(){
        this.dwSize = this.size();
    }	
}

