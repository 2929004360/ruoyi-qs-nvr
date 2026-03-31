package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 行人优先智能化配置列表信息
 * @date 2021/09/27
 */
public class NET_PEDESTRAIN_PRIORITY_INFO extends NetSDKLib.SdkStructure {
	/**
     *  周期编号
     */
    public int              nCycleNum;
    /**
     *  最小优先间隔, 优先执行后的冷却时长，冷却时间内不再响应新的优先请求（1~90）
     */
    public int              nSuspendingPeriod;
    /**
     *  优先配置表个数
     */
    public int              nPhasePriorityConfigNum;
    /**
     *  优先配置表
     */
    public NET_PHASE_PRIORITY_CONFIG_INFO[] stuphasePriorityConfigInfo = (NET_PHASE_PRIORITY_CONFIG_INFO[]) new NET_PHASE_PRIORITY_CONFIG_INFO().toArray(255);
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[256];
}

