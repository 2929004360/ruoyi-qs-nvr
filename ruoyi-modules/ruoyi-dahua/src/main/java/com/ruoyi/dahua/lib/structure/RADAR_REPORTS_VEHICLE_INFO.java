package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  雷达上报车辆信息 
* @date 2022/06/07 09:46:01
*/
public class RADAR_REPORTS_VEHICLE_INFO extends NetSDKLib.SdkStructure {
/** 
车辆ID
*/
    public			int            nVehicleId;
/** 
车辆长度（单位：厘米）
*/
    public			int            nVehicleLength;
/** 
车辆宽度（单位：厘米）
*/
    public			int            nVehicleWidth;
/** 
车辆高度（单位：厘米）
*/
    public			int            nVehicleHeight;
/** 
车辆体积（单位：立方厘米）
*/
    public			int            nVehicleVolume;
/** 
车道号
*/
    public			int            nLaneID;
/** 
车辆行驶方向(相对车道方向) {@link com.ruoyi.dahua.lib.enumeration.EM_VEHICLE_DRIVING_DIRECTION}
*/
    public			int            emDrivingDirection;
/** 
车辆到达雷达检测位置的时间
*/
    public			byte[]         szDetectTime = new byte[32];
/** 
车辆的挡板高度（单位：厘米）
*/
    public			int            nVehicleRailingHigh;
/** 
车辆速度(单位：千米/小时)
*/
    public			int            nVehicleSpeed;
/** 
车辆类型 {@link com.ruoyi.dahua.lib.enumeration.EM_RADAR_DETECTION_VEHICLE_TYPE}
*/
    public			int            emVehicleType;
/** 
车辆车轴个数
*/
    public			int            nAxisNum;
/** 
车辆车轴类型
*/
    public			int            nAxisType;
/** 
保留字节
*/
    public			byte[]         byResverd = new byte[260];

public RADAR_REPORTS_VEHICLE_INFO(){
}
}

