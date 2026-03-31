package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.enumeration.NET_EM_OVER_FLOW_STATE;

/**
 * @author ： 260611
 * @description ： 车道的信息
 * @since ： Created in 2022/03/10 10:12
 */

public class TRAFFIC_QUEUE_INFO extends NetSDKLib.SdkStructure {
    /**
     * 车道号
     */
    public int              nLane;
    /**
     * 排队长度
     */
    public int              nQueueLen;
    /**
     * 排队起始位置，精度1m
     */
    public int              nQueueStartPos;
    /**
     * 检测区域内车辆数
     */
    public int              nQueueVehNum;
    /**
     * 车道中实时车辆平均速度，精度1km/h
     */
    public int              nQueueVehSpeed;
    /**
     * 停止线最近一个线圈的占有时间，从开始占有算起，单位ms
     */
    public int              nOccTime;
    /**
     * 溢出状态。例：如给当前路段设定允许排队长度阀值，实际排队长度超过阀值后就判定当前时刻该路段有溢出。{@link NET_EM_OVER_FLOW_STATE}
     */
    public int              emOverflowState;
    /**
     * 保留字节
     */
    public byte[]           szReserverd = new byte[252];

    @Override
    public String toString() {
        return "TRAFFIC_QUEUE_INFO{" +
                "nLane=" + nLane +
                ", nQueueLen=" + nQueueLen +
                ", nQueueStartPos=" + nQueueStartPos +
                ", nQueueVehNum=" + nQueueVehNum +
                ", nQueueVehSpeed=" + nQueueVehSpeed +
                ", nOccTime=" + nOccTime +
                ", emOverflowState=" + NET_EM_OVER_FLOW_STATE.getNoteByValue(emOverflowState) +
                '}';
    }
}

