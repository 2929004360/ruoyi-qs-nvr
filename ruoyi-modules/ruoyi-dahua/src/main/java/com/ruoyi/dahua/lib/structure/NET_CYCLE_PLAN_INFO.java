package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 周期方案列表信息
 * @date 2021/09/02
 */
public class NET_CYCLE_PLAN_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  周期时长
     */
    public int              nCycle;
    /**
     *  周期方案编号
     */
    public int              nCyclePlanNum;
    /**
     *  周期方案名称
     */
    public byte[]           szCyclePlanName = new byte[32];
    /**
     *  相位差时间
     */
    public int              nOffSet;
    /**
     *  是否相位差协调
     */
    public int              bCoordEnable;
    /**
     *  环列表个数
     */
    public int              nRingNum;
    /**
     *  字节对齐
     */
    public byte[]           szReserved1 = new byte[4];
    /**
     *  环列表
     */
    public NET_RINGS_LIST_INFO[] stuRingInfo = (NET_RINGS_LIST_INFO[]) new NET_RINGS_LIST_INFO().toArray(4);
    /**
     *  跟随相位列表个数
     */
    public int              nOverLapsNum;
    /**
     *  字节对齐
     */
    public byte[]           szReserved2 = new byte[4];
    /**
     *  跟随相位列表
     */
    public NET_OVER_LAPS_INFO[] stuOverLapsInfo = (NET_OVER_LAPS_INFO[]) new NET_OVER_LAPS_INFO().toArray(16);
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[32];
}

