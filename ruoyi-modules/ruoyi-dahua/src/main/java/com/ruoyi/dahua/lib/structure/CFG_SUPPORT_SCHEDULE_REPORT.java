package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 定时上报以及上报周期
 * @date 2021/11/07
 */
public class CFG_SUPPORT_SCHEDULE_REPORT extends NetSDKLib.SdkStructure {
    /**
     *  是否支持定时上报功能
     */
    public int              bEnable;
    /**
     * 有效的上报周期个数
     */
    public int              nSupportPeriodNum;
    /**
     *  设备支持的上报周期颗粒度数组，单位为分钟
     */
    public int[]            nSupportPeriod = new int[16];
}

