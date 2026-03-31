package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author ： 260611
 * @description ： 区域路口过车信息
 * @since ： Created in 2022/03/10 10:12
 */

public class TRAFFIC_FLOW_JUNTION_INFO extends NetSDKLib.SdkStructure {
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
     * 检测断面号
     */
    public int              nSecNo;
    /**
     * 车道号0~7
     */
    public int              nLaneNo;
    /**
     * 车辆类型
     */
    public int              nVehType;
    /**
     * 车辆速度，精度1km/h
     */
    public int              nVehSpeed;
    /**
     * 占有时间，单位ms
     */
    public int              nOccTime;
    /**
     * 保留字节
     */
    public byte[]           szReserverd = new byte[252];

    @Override
    public String toString() {
        return "TRAFFIC_FLOW_JUNTION_INFO{" +
                "nAreaNo=" + nAreaNo +
                ", nJunNo=" + nJunNo +
                ", nDirNo=" + nDirNo +
                ", nLaneDirType=" + nLaneDirType +
                ", nSecNo=" + nSecNo +
                ", nLaneNo=" + nLaneNo +
                ", nVehType=" + nVehType +
                ", nVehSpeed=" + nVehSpeed +
                ", nOccTime=" + nOccTime +
                '}';
    }
}

