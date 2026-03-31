package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author ： 291189
 * @since ： Created in 2021/7/5 15:37
 * CLIENT_AttachPopulationStatistics 的输出参数
 */
public class NET_OUT_ATTACH_GATE_POPULATION_STATISTICS_INFO extends NetSDKLib.SdkStructure {
    public int              dwSize;                               //此结构体大小,必须赋值

    public NET_OUT_ATTACH_GATE_POPULATION_STATISTICS_INFO(){
        this.dwSize=this.size();
    }
}

