package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 车道交通流信息
 * @date 2021/09/01
 */
public class LAN_TRAFFIC_INFO extends NetSDKLib.SdkStructure {
	/**
     *  统计周期(秒)
     */
    public int              nPeriod;
    /**
     *  车道类型
     */
    public int              nLaneType;
    /**
     *  交通流量统计周期内通过的车辆数
     */
    public int              nTrafficFlux;
    /**
     *  断面车辆平均速度单位为千米每小时（km/h）
     */
    public int              nAverageSpeed;
    /**
     *  区间车辆平均速度单位为千米每小时（km/h）
     */
    public int              nIntervalAverageSpeed;
    /**
     *  时间占有率（0-100）
     */
    public int              nTimeOccupyRatio;
    /**
     *  空间占有率（0-100）
     */
    public int              nSpaceOccupyRatio;
    /**
     *  排队长度 单位为米
     */
    public int              nQueueLen;
    /**
     *  预留字节
     */
    public byte[]           byReserved = new byte[128];
}

