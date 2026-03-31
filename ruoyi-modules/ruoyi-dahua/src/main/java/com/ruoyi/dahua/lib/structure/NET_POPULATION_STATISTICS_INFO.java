package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author ： 291189
 * @since ： Created in 2021/7/5
 *人数变化信息
 */
public class NET_POPULATION_STATISTICS_INFO extends NetSDKLib.SdkStructure {
    public int              dwSize;                               // 结构体大小
    public int              nPassPopulation;                      // 正向通过人数
    public int              nMetalAlarmPopulation;                // 正向触发金属报警人数
    public int              nReversePassPopulation;               // 反向通过人数
    public int              nReverseMetalAlarmPopulation;         // 反向触发金属报警人数
    public long             nTempNormalPopulation;                // 体温正常人数
    public long             nTempAlarmPopulation;                 // 体温异常人数

    public NET_POPULATION_STATISTICS_INFO(){
        this.dwSize=this.size();
    }
}

