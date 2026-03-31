package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 周期模式配置
*/
public class CFG_RAINBRUSH_CYCLE extends NetSDKLib.SdkStructure
{
    /**
     * 周期模式下，周期的单位
    */
    public byte[]           szUnit = new byte[32];
    /**
     * 周期模式下，周期长度，单位由Unit字段决定
    */
    public int              nCycleLength;
    /**
     * 周期模式下，雨刷运行的时长，单位：秒
    */
    public int              nRunTime;
    /**
     * 周期模式下，雨刷运行的间隔时间，单位：秒
    */
    public int              nCycleInterval;
    /**
     * 周期模式下，一个周期内完成自清洁操作的次数
    */
    public int              nSelfCleanTimes;
    /**
     * 保留字段
    */
    public byte[]           szReserved = new byte[1024];

    public CFG_RAINBRUSH_CYCLE() {
    }
}

