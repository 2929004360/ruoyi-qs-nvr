package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 可变车道计划表信息
 * @date 2021/09/28
 */
public class NET_VARLANE_PLAN_LIST_INFO extends NetSDKLib.SdkStructure {
	/**
     *  可变车道方案编号（0表示未配置）
     */
    public int              nVarlanePatternNumber;
    /**
     *  可变车道方案名称
     */
    public byte[]           szVarlanePatternName = new byte[64];
    /**
     *  计划表个数
     */
    public int              nLanePlanNum;
    /**
     *  计划表
     */
    public NET_LANE_PLAN_LIST_INFO[] stuLanePlanInfo = (NET_LANE_PLAN_LIST_INFO[]) new NET_LANE_PLAN_LIST_INFO().toArray(12);
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[32];
}

