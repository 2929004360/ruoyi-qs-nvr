package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * 高德测温模块使用参数
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/09 17:05
 */
public class NET_GUIDE_MODULE_MEASURE_MODE_PARAM extends NetSDKLib.SdkStructure {
    /**
     *  测温区域框显示使能（在视频流界面显示测试区域框）	
     */
    public int              bRectEnable;
    /**
     *  允许的最远测温距离（cm）
     */
    public int              nMaxDistance;
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
     *  温度随机替换阈值（低于此阈值的温度会被随机替换为一个有效温度，用于兼容错误情况。阈值为0时表示此功能不开启）。
     */
    public double           dbTempRandReplaceThreshold;
    /**
     *  是否开启测温调试模式（在人脸框顶部显示测温数据）
     */
    public int              bDebugModelEnable;
    /**
     *  热成像标定模式，不同标定模式下的标定参数不一样
     */
    public int              emCalibrationMode;
    /**
     *  是否显示热图
     */
    public int              bHeatDisplayEnbale;
    /**
     *  预留字段
     */
    public byte[]           byReserved = new byte[1020];

    @Override
    public String toString() {
        return "NET_GUIDE_MODULE_MEASURE_MODE_PARAM{" +
                "bRectEnable=" + bRectEnable +
                ", nMaxDistance=" + nMaxDistance +
                ", dbTempThreshold=" + dbTempThreshold +
                ", dbCorrectTemp=" + dbCorrectTemp +
                ", dbValidTempLowerLimit=" + dbValidTempLowerLimit +
                ", dbTempRandReplaceThreshold=" + dbTempRandReplaceThreshold +
                ", bDebugModelEnable=" + bDebugModelEnable +
                ", emCalibrationMode=" + emCalibrationMode +
                ", bHeatDisplayEnbale=" + bHeatDisplayEnbale +
                '}';
    }
}

