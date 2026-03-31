package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 夜间相位配置表信息
 * @date 2021/09/28
 */
public class NET_PHASE_CONFIG_NIGHT_INFO extends NetSDKLib.SdkStructure {
	/**
     *  相位编号，仅支持单环
     */
    public int              nPhaseNum;
    /**
     *  相位重复出现的次数
     */
    public int              nPhaseOrder;
    /**
     *  是否驻留
     */
    public int              bHold;
    /**
     *  最大请求间隔（0~160）
     */
    public int              nMaxInterval;
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[32];
}

