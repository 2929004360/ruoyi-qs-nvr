package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 相位配置信息
 * @date 2021/09/28
 */
public class NET_PHASE_CONFIG_INFO extends NetSDKLib.SdkStructure {
	/**
     *  相位编号，仅支持单环
     */
    public int              nPhaseNum;
    /**
     *  相位重复出现的次数
     */
    public int              nPhaseOrder;
    /**
     *  响应使能
     */
    public int              bRespondEnable;
    /**
     *  缩减比例（0~0.5）
     */
    public float            fReduceRatio;
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[32];
}

