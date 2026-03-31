package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * @author 251823
 * @description 夜间请求智能化配置列表信息
 * @date 2021/09/28
 */
public class NET_NIGHT_ASK_INFO extends NetSDKLib.SdkStructure {
	/**
     *  周期编号
     */
    public int              nCycleNum;
    /**
     *  夜间相位配置表个数
     */
    public int              nPhaseConfigNum;
    /**
     *  夜间相位配置表
     */
    public NET_PHASE_CONFIG_NIGHT_INFO[] stuPhaseConfigNightInfo = (NET_PHASE_CONFIG_NIGHT_INFO[]) new NET_PHASE_CONFIG_NIGHT_INFO().toArray(16);
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[32];
}

