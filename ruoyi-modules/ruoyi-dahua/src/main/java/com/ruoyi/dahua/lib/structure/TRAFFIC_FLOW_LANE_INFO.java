package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author ： 260611
 * @description ： 车道的车道统计信息
 * @since ： Created in 2022/03/10 10:12
 */

public class TRAFFIC_FLOW_LANE_INFO extends NetSDKLib.SdkStructure {
    /**
     * 车道流量
     */
    public int              nFlow;
    /**
     * 车道平均速度，1km/h精度
     */
    public int              nAveSpeed;
    /**
     * 车道占有率
     */
    public int              nOccupancy;
    /**
     * 保留字节
     */
    public byte[]           szReserverd = new byte[252];

    @Override
    public String toString() {
        return "TRAFFIC_FLOW_LANE_INFO{" +
                "nFlow=" + nFlow +
                ", nAveSpeed=" + nAveSpeed +
                ", nOccupancy=" + nOccupancy +
                '}';
    }
}

