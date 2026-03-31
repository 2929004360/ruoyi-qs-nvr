package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

import java.util.Arrays;

/**
 * @author ： 260611
 * @description ： NET_TRAFFIC_FLOW_STATE
 * @since ： Created in 2022/03/10 10:12
 */

public class NET_TRAFFIC_FLOW_STATE extends NetSDKLib.SdkStructure {
    /**
     * 车道号
     */
    public int              nLane;
    /**
     * 状态值  若emJamState字段为有效值(不为 JAM_STATUS_UNKNOW) ,则dwState字段无效
     */
    public int              dwState;
    /**
     * 流量值, 单位: 辆
     */
    public int              dwFlow;
    /**
     * 流量值对应的统计时间, 单位:分钟。与dwPeriodByMili一起使用。
     */
    public int              dwPeriod;
    /**
     * 车道方向信息
     */
    public NET_TRAFFIC_FLOWSTAT_INFO_DIR stTrafficFlowDir = new NET_TRAFFIC_FLOWSTAT_INFO_DIR();
    /**
     * 通过车辆总数
     */
    public int              nVehicles;
    /**
     * 平均车速,单位km/h
     */
    public float            fAverageSpeed;
    /**
     * 平均车长,单位米
     */
    public float            fAverageLength;
    /**
     * 时间占有率,即单位时间内通过断面的车辆所用时间的总和占单位时间的比例
     */
    public float            fTimeOccupyRatio;
    /**
     * 空间占有率,即按百分率计量的车辆长度总和除以时间间隔内车辆平均行驶距离
     */
    public float            fSpaceOccupyRatio;
    /**
     * 车头间距,相邻车辆之间的距离,单位米/辆
     */
    public float            fSpaceHeadway;
    /**
     * 车头时距,单位秒/辆
     */
    public float            fTimeHeadway;
    /**
     * 车辆密度,每公里的车辆数,单位辆/km
     */
    public float            fDensity;
    /**
     * 超速车辆数
     */
    public int              nOverSpeedVehicles;
    /**
     * 低速车辆数
     */
    public int              nUnderSpeedVehicles;
    /**
     * 大车交通量(9米<车长<12米),辆/单位时间
     */
    public int              nLargeVehicles;
    /**
     * 中型车交通量(6米<车长<9米),辆/单位时间
     */
    public int              nMediumVehicles;
    /**
     * 小车交通量(4米<车长<6米),辆/单位时间,
     */
    public int              nSmallVehicles;
    /**
     * 摩托交通量(微型车,车长<4米),辆/单位时间,
     */
    public int              nMotoVehicles;
    /**
     * 超长交通量(车长>=12米),辆/单位时间,
     */
    public int              nLongVehicles;
    /**
     * 交通量, 辆/单位时间, 某时间间隔通过车道、道路或其他通道上一点的车辆数,常以1小时计,
     */
    public int              nVolume;
    /**
     * 流率小车当量,辆/小时, 车辆通过车道、道路某一断面或某一路段的当量小时流量
     */
    public int              nFlowRate;
    /**
     * 排队长度,单位：米, 从信号交叉口停车线到上游排队车辆末端之间的距离(建议废掉 改用dBackOfQueue下面)
     */
    public int              nBackOfQueue;
    /**
     * 旅行时间,单位：秒, 车辆通过某一条道路所用时间。包括所有停车延误
     */
    public int              nTravelTime;
    /**
     * 延误,单位：秒,驾驶员、乘客或行人花费的额外的行程时间
     */
    public int              nDelay;
    /**
     * 车道方向,详见NET_ROAD_DIRECTION
     */
    public byte[]           byDirection = new byte[16];
    /**
     * 车道行驶方向个数
     */
    public byte             byDirectionNum;
    /**
     * 字节对齐
     */
    public byte[]           reserved1 = new byte[3];
    /**
     * 道路拥挤状况  若此字段为有效值(不为 JAM_STATUS_UNKNOW) ,则以此字段为准, dwState字段无效
     */
    public int              emJamState;
    /**
     * 客车交通量(辆/单位时间)
     */
    public int              nPassengerCarVehicles;
    /**
     * 大货车交通量(辆/单位时间)
     */
    public int              nLargeTruckVehicles;
    /**
     * 中货车交通量(辆/单位时间)
     */
    public int              nMidTruckVehicles;
    /**
     * 轿车交通量(辆/单位时间)
     */
    public int              nSaloonCarVehicles;
    /**
     * 面包车交通量(辆/单位时间)
     */
    public int              nMicrobusVehicles;
    /**
     * 小货车交通量(辆/单位时间)
     */
    public int              nMicroTruckVehicles;
    /**
     * 三轮车交通量(辆/单位时间)
     */
    public int              nTricycleVehicles;
    /**
     * 摩托车交通量(辆/单位时间)
     */
    public int              nMotorcycleVehicles;
    /**
     * 行人交通量(辆/单位时间)
     */
    public int              nPasserbyVehicles;
    /**
     * 道路等级
     */
    public int              emRank;
    /**
     * 流量状态
     */
    public int              nState;
    /**
     * 车头虚拟线圈是否被占用 TURE表示占用，FALSE表示未占用
     */
    public int              bOccupyHeadCoil;
    /**
     * 车尾虚拟线圈是否被占用 TURE表示占用，FALSE表示未占用
     */
    public int              bOccupyTailCoil;
    /**
     * 流量数据是否有效 TURE表示有效，FALSE表示无效
     */
    public int              bStatistics;
    /**
     * 左转车辆总数,单位:分钟
     */
    public int              nLeftVehicles;
    /**
     * 右转车辆总数,单位:分钟
     */
    public int              nRightVehicles;
    /**
     * 直行车辆总数,单位:分钟
     */
    public int              nStraightVehicles;
    /**
     * 掉头车辆总数,单位:分钟
     */
    public int              nUTurnVehicles;
    /**
     * 每个车道的最后一辆车坐标,采用8192坐标系
     */
    public NetSDKLib.NET_POINT stQueueEnd = new NetSDKLib.NET_POINT();
    /**
     * 排队长度,单位：米, 从信号交叉口停车线到上游排队车辆末端之间的距离
     */
    public double           dBackOfQueue;
    /**
     * 流量值的毫秒时间,值不超过60000,和dwPeriod一起使用,流量值总时间:dwPeriod*60*1000+dwPeriodByMili(单位：毫秒)
     */
    public int              dwPeriodByMili;
    /**
     * 公交车交通量(辆/单位时间)
     */
    public int              nBusVehicles;
    /**
     * MPV交通量(辆/单位时间)
     */
    public int              nMPVVehicles;
    /**
     * 中客车交通量(辆/单位时间)
     */
    public int              nMidPassengerCarVehicles;
    /**
     * 微型轿车交通量(辆/单位时间)
     */
    public int              nMiniCarriageVehicles;
    /**
     * 油罐车交通量(辆/单位时间)
     */
    public int              nOilTankTruckVehicles;
    /**
     * 皮卡车交通量(辆/单位时间)
     */
    public int              nPickupVehicles;
    /**
     * SUV交通量(辆/单位时间)
     */
    public int              nSUVVehicles;
    /**
     * SUV或者MPV交通量(辆/单位时间)
     */
    public int              nSUVorMPVVehicles;
    /**
     * 槽罐车交通量(辆/单位时间)
     */
    public int              nTankCarVehicles;
    /**
     * 未知车辆交通量(辆/单位时间)
     */
    public int              nUnknownVehicles;
    /**
     * 车道流量信息属性
     */
    public int              emCustomFlowAttribute;
    /**
     * 道路空闲长度，例：如设定路段长度为100米，实际检测到排队长度为30米，那么道路空闲长度就为70米，单位：米
     */
    public int              nRoadFreeLength;
    /**
     * 溢出状态。例：如给当前路段设定允许排队长度阀值，实际排队长度超过阀值后就判定当前时刻该路段有溢出。
     */
    public int              emOverflowState;
    /**
     * / 排队车辆数，单位：辆
     */
    public int              nQueueVehicleNum;
    /**
     * / 空间占有率统计个数
     */
    public int              nSpaceOccupyRatioMultiCount;
    /**
     * / 空间占有率统计信息
     */
    public NET_SPACE_OCCUPY_RATIO_MULTI[] stuSpaceOccupyRatioMulti = new NET_SPACE_OCCUPY_RATIO_MULTI[32];
    /**
     * / 停车次数，周期内该车道内所有目标的总停车次数，单位:次
     */
    public int              nTotalStopNum;
    /**
     * / 延误时间，周期内该车道内所有目标的总延误时间，单位：毫秒
     */
    public int              nTotalDelayTime;
    /**
     * / 目标总数量，周期内进入该车道内所有目标总数量，单位 辆
     */
    public int              nTotalNum;
    /**
     * / 平均停车次数，周期内该车道所有目标的平均停车次数。单位：次
     */
    public float            fAverageStopNum;
    /**
     * / 平均延误时间，周期内该车道所有目标的平均延误时间。单位：毫秒
     */
    public float            fAverageDelayTime;
    /**
     * / 流量饱和度，该车道内的流量饱和度
     */
    public float            fFlowSaturationRatio;
    /**
     * / 最大排队长度，周期内最大排队长度，单位:米
     */
    public float            fMaxQueueLen;
    /**
     * / 排队开始位置，排队时队首距设备的位置，以设备方向为正，负数表示队首在设备后方，单位：米
     */
    public float            fQueueStartingPoint;
    /**
     * / 排队结束位置，排队时队尾距设备的位置，以设备方向为正，负数表示队尾在设备后方，单位：米
     */
    public float            fQueueFinishingPoint;
    /**
     * / 空间占有目标个数，即区域车辆数，该车道内指定区域的车辆数目，支持车道内多区域统计，不同区域车辆数使用|符号分隔。
     */
    public byte[]           szSpaceOccupyNum = new byte[64];
    /**
     * / 云台预置点,球机预置点必大于0
     */
    public int              nPresetID;
    /**
     * / 保留字节
     */
    public byte[]           reserved = new byte[88];

    @Override
    public String toString() {
        return "NET_TRAFFIC_FLOW_STATE{" +
                "nLane=" + nLane +
                ", dwState=" + dwState +
                ", dwFlow=" + dwFlow +
                ", dwPeriod=" + dwPeriod +
                ", stTrafficFlowDir=" + stTrafficFlowDir.toString() +
                ", nVehicles=" + nVehicles +
                ", fAverageSpeed=" + fAverageSpeed +
                ", fAverageLength=" + fAverageLength +
                ", fTimeOccupyRatio=" + fTimeOccupyRatio +
                ", fSpaceOccupyRatio=" + fSpaceOccupyRatio +
                ", fSpaceHeadway=" + fSpaceHeadway +
                ", fTimeHeadway=" + fTimeHeadway +
                ", fDensity=" + fDensity +
                ", nOverSpeedVehicles=" + nOverSpeedVehicles +
                ", nUnderSpeedVehicles=" + nUnderSpeedVehicles +
                ", nLargeVehicles=" + nLargeVehicles +
                ", nMediumVehicles=" + nMediumVehicles +
                ", nSmallVehicles=" + nSmallVehicles +
                ", nMotoVehicles=" + nMotoVehicles +
                ", nLongVehicles=" + nLongVehicles +
                ", nVolume=" + nVolume +
                ", nFlowRate=" + nFlowRate +
                ", nBackOfQueue=" + nBackOfQueue +
                ", nTravelTime=" + nTravelTime +
                ", nDelay=" + nDelay +
                ", byDirection=" + Arrays.toString(byDirection) +
                ", byDirectionNum=" + byDirectionNum +
                ", emJamState=" + emJamState +
                ", nPassengerCarVehicles=" + nPassengerCarVehicles +
                ", nLargeTruckVehicles=" + nLargeTruckVehicles +
                ", nMidTruckVehicles=" + nMidTruckVehicles +
                ", nSaloonCarVehicles=" + nSaloonCarVehicles +
                ", nMicrobusVehicles=" + nMicrobusVehicles +
                ", nMicroTruckVehicles=" + nMicroTruckVehicles +
                ", nTricycleVehicles=" + nTricycleVehicles +
                ", nMotorcycleVehicles=" + nMotorcycleVehicles +
                ", nPasserbyVehicles=" + nPasserbyVehicles +
                ", emRank=" + emRank +
                ", nState=" + nState +
                ", bOccupyHeadCoil=" + bOccupyHeadCoil +
                ", bOccupyTailCoil=" + bOccupyTailCoil +
                ", bStatistics=" + bStatistics +
                ", nLeftVehicles=" + nLeftVehicles +
                ", nRightVehicles=" + nRightVehicles +
                ", nStraightVehicles=" + nStraightVehicles +
                ", nUTurnVehicles=" + nUTurnVehicles +
                ", stQueueEnd=" + stQueueEnd.toString() +
                ", dBackOfQueue=" + dBackOfQueue +
                ", dwPeriodByMili=" + dwPeriodByMili +
                ", nBusVehicles=" + nBusVehicles +
                ", nMPVVehicles=" + nMPVVehicles +
                ", nMidPassengerCarVehicles=" + nMidPassengerCarVehicles +
                ", nMiniCarriageVehicles=" + nMiniCarriageVehicles +
                ", nOilTankTruckVehicles=" + nOilTankTruckVehicles +
                ", nPickupVehicles=" + nPickupVehicles +
                ", nSUVVehicles=" + nSUVVehicles +
                ", nSUVorMPVVehicles=" + nSUVorMPVVehicles +
                ", nTankCarVehicles=" + nTankCarVehicles +
                ", nUnknownVehicles=" + nUnknownVehicles +
                ", emCustomFlowAttribute=" + emCustomFlowAttribute +
                ", nRoadFreeLength=" + nRoadFreeLength +
                ", emOverflowState=" + emOverflowState +
                '}';
    }
}

