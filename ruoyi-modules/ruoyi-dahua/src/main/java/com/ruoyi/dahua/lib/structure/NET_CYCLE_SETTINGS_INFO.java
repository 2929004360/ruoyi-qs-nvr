package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 周期设置信息
 * @date 2021/09/28
 */
public class NET_CYCLE_SETTINGS_INFO extends NetSDKLib.SdkStructure {
	/**
     *  周期上限，最大饱和度对应的周期值nMaxCyc（30 ~ 240）
     */
    public int              nCycleMax;
    /**
     *  周期下限，最小饱和度对应的周期值nMinCyc（30 ~ 240）
     */
    public int              nCycleMin;
    /**
     *  饱和度约束上限（0.000~1.000）
     */
    public float            fSaturitionUpLimit;
    /**
     *  饱和度约束下限（0.000~1.000）
     */
    public float            fSaturitionDnLimit;
    /**
     *  周期锁定约束上限（30 ~ 240）
     */
    public int              nCycleUpLimit;
    /**
     *  周期锁定约束下限（30 ~ 240）
     */
    public int              nCycleDnLimit;
    /**
     *  周期上升最大步长（1 ~ 20）
     */
    public int              nCycleUpStep;
    /**
     *  周期下降最大步长（1 ~ 20）
     */
    public int              nCycleDnStep;
    /**
     *  平滑周期饱和度个数（1 ~ 5）
     */
    public int              nSmoothCycNum;
    /**
     *  是否允许双周期:0不允许 1允许
     */
    public int              nPermitDoubleCyc;
    /**
     *  触发双周期的阈值（60 ~ 240）
     */
    public int              nDoubleCycThreshold;
    /**
     *  预留字节 
     */
    public byte[]           szReserved = new byte[32];
}

