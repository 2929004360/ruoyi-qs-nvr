package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.callback.securityCheck.fNotifyPopulationStatisticsInfo;
import com.sun.jna.Pointer;

/**
 * @author ： 291189
 * @since ： Created in 2021/7/5
 *接口 CLIENT_AttachPopulationStatistics 的输入参数
 */
public class NET_IN_ATTACH_GATE_POPULATION_STATISTICS_INFO extends NetSDKLib.SdkStructure {
    public int              dwSize;                               // 结构体大小
    public fNotifyPopulationStatisticsInfo cbNotifyPopulationStatisticsInfo; // 回调函数,有人数变化信息时,回调给上层
    public Pointer          dwUser;                               // 用户自定义参数

    public NET_IN_ATTACH_GATE_POPULATION_STATISTICS_INFO() {
        this.dwSize=this.size();
    }
}

