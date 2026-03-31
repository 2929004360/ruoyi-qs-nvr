package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 可变车道日方案列表信息
 * @date 2021/09/28
 */
public class NET_DAY_PLANS_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  日方案个数
     */
    public int              nDayPlanNumber;
    /**
     *  日方案编号，从1开始
     */
    public byte[]           szDayPlanName = new byte[64];
    /**
     *  日计划时段表名称
     */
    public int              nDayActionNum;
    /**
     *  日计划时段表 
     */
    public NET_DAY_ACTIONS_INFO[] stuDayActionInfo = (NET_DAY_ACTIONS_INFO[]) new NET_DAY_ACTIONS_INFO().toArray(24);
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[32];
}

