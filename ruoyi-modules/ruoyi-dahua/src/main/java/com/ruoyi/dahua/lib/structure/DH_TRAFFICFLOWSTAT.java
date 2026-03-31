package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @description 流量统计记录
 * @date 2022/05/07 10:07:05
 */
public class DH_TRAFFICFLOWSTAT extends NetSDKLib.SdkStructure {
	/**
	 * 同DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO.MachineGroup
	 */
    public byte[]           szMachineAddress = new byte[256];
	/**
	 * 同DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO.MachineName
	 */
    public byte[]           szMachineName = new byte[256];
	/**
	 * 行驶方向"Approach"-上行,即车辆离设备部署点越来越近；"Leave"-下行,即车辆离设备部署点越来越远,第二和第三个参数分别代表上行和下行的两个地点,UTF-8编码
	 */
    public byte[]           szDrivingDirection = new byte[3 * 32];
	/**
	 * 车道号 使用用户配置的车道编号
	 */
    public int              nLane;
	/**
	 * 统计时间,转换到UTC
	 */
    public NET_TIME_EX      UTC = new NET_TIME_EX();
	/**
	 * 统计周期,单位分钟
	 */
    public int              nPeriod;
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
	 * 大车数量 车辆类型判断标准参见TrafficSnapshot配置表
	 */
    public int              nLargeVehicles;
	/**
	 * 中型车数量
	 */
    public int              nMediumVehicles;
	/**
	 * 小车数量
	 */
    public int              nSmallVehicles;
	/**
	 * 摩托车数量
	 */
    public int              nMotoVehicles;
	/**
	 * 超长车数量
	 */
    public int              nLongVehicles;
	/**
	 * 流量数据所属通道号
	 */
    public byte[]           szChannel = new byte[64];
	/**
	 * 保留字段
	 */
    public byte[]           zResvered = new byte[1024];

	public DH_TRAFFICFLOWSTAT() {
	}
}

