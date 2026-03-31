package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 溢出控制智能化配置列表信息
 * @date 2021/09/28
 */
public class NET_OVER_FLOW_CONTROL_INFO extends NetSDKLib.SdkStructure {
	/**
     *  周期编号
     */
    public int              nCycleNum;
    /**
     *  通用配置
     */
    public NET_COMMON_CONFIG_INFO stuCommnCofigInfo;
    /**
     *  检测器配置个数
     */
    public int              nDetectConfigNum;
    /**
     *  检测器配置
     */
    public NET_DETECTOR_CONFIG_INFO[] stuDetectConfigInfo = (NET_DETECTOR_CONFIG_INFO[]) new NET_DETECTOR_CONFIG_INFO().toArray(32);
    /**
     *  相位配置个数
     */
    public int              nPhaseConfigNum;
    /**
     *  相位配置
     */
    public NET_PHASE_CONFIG_INFO[] stuPhaseConfigInfo = (NET_PHASE_CONFIG_INFO[]) new NET_PHASE_CONFIG_INFO().toArray(16);
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[32];
}

