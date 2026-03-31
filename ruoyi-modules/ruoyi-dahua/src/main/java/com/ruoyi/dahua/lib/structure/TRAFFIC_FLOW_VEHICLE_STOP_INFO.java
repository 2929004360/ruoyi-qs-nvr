package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author ： 260611
 * @description ： 区域路口停车信息
 * @since ： Created in 2022/03/10 10:12
 */

public class TRAFFIC_FLOW_VEHICLE_STOP_INFO extends NetSDKLib.SdkStructure {
    /**
     * 区域号
     */
    public int              nAreaNo;
    /**
     * 路口号
     */
    public int              nJunNo;
    /**
     * 方向号，北东南西分别为0/1/2/3
     */
    public int              nDirNo;
    /**
     * 进出口类型，进口道 0，出口道 1
     */
    public int              nLaneDirType;
    /**
     * 车道号0~7
     */
    public int              nLaneNo;
    /**
     * 停车位置，精度1m
     */
    public int              nStopPos;
    /**
     * 停车次数，第几次停车
     */
    public int              nStopTimes;
    /**
     * 停车占有时间，单位ms
     */
    public int              nStopOccTime;
    /**
     * 保留字节
     */
    public byte[]           szReserverd = new byte[256];

    @Override
    public String toString() {
        return "TRAFFIC_FLOW_VEHICLE_STOP_INFO{" +
                "nAreaNo=" + nAreaNo +
                ", nJunNo=" + nJunNo +
                ", nDirNo=" + nDirNo +
                ", nLaneDirType=" + nLaneDirType +
                ", nLaneNo=" + nLaneNo +
                ", nStopPos=" + nStopPos +
                ", nStopTimes=" + nStopTimes +
                ", nStopOccTime=" + nStopOccTime +
                '}';
    }
}

