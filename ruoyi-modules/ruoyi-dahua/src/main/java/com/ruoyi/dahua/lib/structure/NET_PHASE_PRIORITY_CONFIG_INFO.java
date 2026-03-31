package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 优先配置表信息
 * @date 2021/09/27
 */
public class NET_PHASE_PRIORITY_CONFIG_INFO extends NetSDKLib.SdkStructure {
	/**
     *  序号
     */
    public int              nId;
    /**
     *  相位编号，仅支持单环
     */
    public int              nPhaseNum;
    /**
     *  相位重复出现的次数
     */
    public int              nPhaseOrder;
    /**
     *  相位切换阈值
     */
    public int              nMinGreenThr;
    /**
     *  跳相阈值
     */
    public int              nSkipThr;
    /**
     *  启用检测器编号个数
     */
    public int              nEnbleDetectorsNum;
    /**
     *  启用检测器编号
     */
    public int[]            nEnbleDetectors = new int[16];
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[32];
}

