package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * 单点手腕测温模式参数
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/09 17:05
 */
public class NET_WRIST_MEASURE_MODE_PARAM extends NetSDKLib.SdkStructure {
    /**
     *  温度阈值（摄氏度）
     */
    public double           dbTempThreshold;
    /**
     *  温度矫正值（摄氏度）
     */
    public double           dbCorrectTemp;
    /**
     *  有效温度下限，低于此值的温度为无效温度（摄氏度）
     */
    public double           dbValidTempLowerLimit;
    /**
     *  测温超时时间（秒）
     */
    public int              nMeasureTimeout;
    /**
     *  有效温度测量距离（cm），小于该距离测量到的温度认为有效
     */
    public int              nValidMeasureDistance;
    /**
     *  无效温度测量距离（cm），大于该距离测量到的温度认为无效，直接过滤，同时该字段还配合ValidTemperatureDistance字段使用，在有效和无效距离之间测量的温度是不准的，需要提示用户靠近一点。
     */
    public int              nInvalidMeasureDistance;
    /**
     * 预留字段
     */
    public byte[]           byReserved = new byte[1020];

    @Override
    public String toString() {
        return "NET_WRIST_MEASURE_MODE_PARAM{" +
                "dbTempThreshold=" + dbTempThreshold +
                ", dbCorrectTemp=" + dbCorrectTemp +
                ", dbValidTempLowerLimit=" + dbValidTempLowerLimit +
                ", nMeasureTimeout=" + nMeasureTimeout +
                ", nValidMeasureDistance=" + nValidMeasureDistance +
                ", nInvalidMeasureDistance=" + nInvalidMeasureDistance +
                '}';
    }
}

