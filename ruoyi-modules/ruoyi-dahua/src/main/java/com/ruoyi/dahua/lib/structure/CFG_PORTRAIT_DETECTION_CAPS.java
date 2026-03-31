package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.enumeration.EM_SNAP_POLICY_TYPE;

/**
 * 人像检测信息
 *
 * @author 47040
 * @since Created in 2021/4/25 19:55
 */
public class CFG_PORTRAIT_DETECTION_CAPS extends NetSDKLib.SdkStructure {
    /**
     * 抓拍策略取值个数
     */
    public int              nSnapPolicyNum;
    /**
     * 抓拍策略取值 {@link EM_SNAP_POLICY_TYPE}
     */
    public int[]            emSnapPolicyType = new int[8];
    /**
     * 是否支持合规检测 0/1 不支持、支持
     */
    public int              bCompliantDetectSupport;
    /**
     * 预留字节
     */
    public byte[]           byReserved = new byte[256];
}

