package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 可变车道日方案列表信息
 * @date 2021/09/27
 */
public class NET_DAY_PLAN_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  日计划编号，从1开始
     */
    public int              nDayPlanNumber;
    /**
     *  日计划名称
     */
    public byte[]           szDayPlanName = new byte[32];
    /**
     *  字节对齐
     */
    public byte[]           szReserved1 = new byte[4];
    /**
     *  时段计划列表个数
     */
    public int              nDayActionNum;
    /**
     *  时段计划列表
     */
    public NET_DAY_ACTION_INFO[] stuDayActionInfo = (NET_DAY_ACTION_INFO[]) new NET_DAY_ACTION_INFO().toArray(24);
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[32];
}

