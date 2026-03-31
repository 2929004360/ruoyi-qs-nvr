package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.enumeration.EM_DETECT_SENSOR_TYPE;
import com.ruoyi.dahua.lib.enumeration.EM_TRAFFIC_FLOW_STATUS;
import com.ruoyi.dahua.lib.enumeration.EM_VIRTUAL_COIL_OCCUPANCY_STATUS;

/**
 * @author ： 47040
 * @since ： Created in 2020/12/17 11:38
 */
public class NET_TRAFFIC_FLOW_STAT extends NetSDKLib.SdkStructure {
    /**
     * 物理车道号
     */
    public int              nLane;
    /**
     * 自定义车道号
     */
    public int              nRoadwayNumber;
    /**
     * 流量状态 枚举 {@link EM_TRAFFIC_FLOW_STATUS}
     */
    public int              emStatus;
    /**
     * 车头虚拟线圈状态, 即车进线圈 枚举 {@link EM_VIRTUAL_COIL_OCCUPANCY_STATUS}
     */
    public int              emHeadCoil;
    /**
     * 车尾虚拟线圈状态, 即车出线圈 枚举 {@link EM_VIRTUAL_COIL_OCCUPANCY_STATUS}
     */
    public int              emTailCoil;
    /**
     * 车道平均速度(单位：km/h)
     */
    public int              nSpeed;
    /**
     * 排队长度(单位：cm)
     */
    public int              nQueueLen;
    /**
     * 排队车辆数
     */
    public int              nCarsInQueue;
    /**
     * 探测物体的传感器类型 {@link EM_DETECT_SENSOR_TYPE}
     */
    public int              emSensorType;
    /**
     * 车头间距，相邻车辆之间的距离，单位米/辆
     */
    public double           dbSpaceHeadway;
    /**
     * 车头时距，单位秒/辆
     */
    public double           dbTimeHeadWay;
    /**
     * 空间占有率，即按百分率计量的车辆长度总和除以时间间隔内车辆平均行驶距离
     */
    public double           dbSpaceOccRatio;
    /**
     * 车道中的所有车辆的数量，即车流量
     */
    public  int             nCarVehicles;
    /**
     * 保留字节
     */
    public byte[]           byReserverd = new byte[228];

    @Override
    public String toString() {
        return "NET_TRAFFIC_FLOW_STAT{" +
                "nLane=" + nLane +
                ", nRoadwayNumber=" + nRoadwayNumber +
                ", emStatus=" + emStatus +
                ", emHeadCoil=" + emHeadCoil +
                ", emTailCoil=" + emTailCoil +
                ", nSpeed=" + nSpeed +
                ", nQueueLen=" + nQueueLen +
                ", nCarsInQueue=" + nCarsInQueue +
                ", emSensorType=" + emSensorType +
                ", dbSpaceHeadway=" + dbSpaceHeadway +
                ", dbTimeHeadWay=" + dbTimeHeadWay +
                ", dbSpaceOccRatio=" + dbSpaceOccRatio +
                ", nCarVehicles=" + nCarVehicles +
                '}';
    }
}

