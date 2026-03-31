package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/** 
* @author 291189
* @description  事件类型 EVENT_IVS_TRAFFIC_CAR_MEASUREMENT(交通卡口测量(车辆长、宽、高度、重量等)事件) 对应数据 
* @date 2022/06/07 09:46:05
*/
public class DEV_EVENT_TRAFFIC_CAR_MEASUREMENT_INFO extends NetSDKLib.SdkStructure {
/** 
通道号
*/
    public			int            nChannelID;
/** 
0:脉冲
*/
    public			int            nAction;
/** 
事件名称
*/
    public			byte[]         szName = new byte[128];
/** 
时间戳(单位是毫秒)
*/
    public			double         PTS;
/** 
事件发生的时间
*/
    public NET_TIME_EX      UTC = new NET_TIME_EX();
/** 
事件对应文件信息
*/
    public NetSDKLib.NET_EVENT_FILE_INFO stuFileInfo = new NetSDKLib.NET_EVENT_FILE_INFO();
/** 
事件ID
*/
    public			int            nEventID;
/** 
车速,单位km/h
*/
    public			int            nSpeed;
/** 
触发类型 {@link com.ruoyi.dahua.lib.enumeration.EM_TRIGGER_TYPE}
*/
    public			int            emTriggerType;
/** 
触发时机 {@link com.ruoyi.dahua.lib.enumeration.EM_TRIGGER_OCCUR_TYPE}
*/
    public			int            emTriggerOccur;
/** 
标记抓拍帧
*/
    public			int            nMark;
/** 
视频分析的数据源地址
*/
    public			int            nSource;
/** 
视频分析帧序号
*/
    public			int            nFrameSequence;
/** 
车道号
*/
    public			int            nLaneID;
/** 
红灯开始的时间
*/
    public NET_TIME_EX      stuRedLightStartTime = new NET_TIME_EX();
/** 
抓拍过程 {@link com.ruoyi.dahua.lib.enumeration.EM_CAPTURE_PROCESS_END_TYPE}
*/
    public			int            emCaptureProcess;
/** 
卡片信息
*/
    public NetSDKLib.EVENT_CARD_INFO stuCardInfo = new NetSDKLib.EVENT_CARD_INFO();
/** 
车辆行驶方向(相对车道方向) {@link com.ruoyi.dahua.lib.enumeration.EM_VEHICLE_DRIVING_DIRECTION}
*/
    public			int            emDrivingDirection;
/** 
交通信号灯类型 {@link com.ruoyi.dahua.lib.enumeration.EM_TRFAFFIC_LIGHT_TYPE}
*/
    public			int            emLightState;
/** 
开闸状态 {@link NetSDKLib.EM_OPEN_STROBE_STATE}
*/
    public			int            emOpenStrobeState;
/** 
抓拍车的位置 {@link NetSDKLib.EM_VEHICLE_DIRECTION}
*/
    public			int            emVehicleDirection;
/** 
主驾驶座位安全带状态 {@link NetSDKLib.NET_SAFEBELT_STATE}
*/
    public			int            emMainSeat;
/** 
副驶座位安全带状态 {@link NetSDKLib.NET_SAFEBELT_STATE}
*/
    public			int            emSlaveSeat;
/** 
车辆信息，记录了车头、车尾车牌号和车牌颜色
*/
    public EVENT_PLATE_INFO stuPlateInfo = new EVENT_PLATE_INFO();
/** 
称重系统车辆信息
*/
    public			NET_CAR_WEIGHT_INFO stuCarWeightInfo = new NET_CAR_WEIGHT_INFO();
/** 
雷达上报车辆信息
*/
    public			RADAR_REPORTS_VEHICLE_INFO stuRadarReportsVehicleInfo = new RADAR_REPORTS_VEHICLE_INFO();
/** 
公共信息
*/
    public NetSDKLib.EVENT_COMM_INFO stuCommInfo = new NetSDKLib.EVENT_COMM_INFO();
/** 
检测到的物体信息
*/
    public NetSDKLib.DH_MSG_OBJECT stuObject = new NetSDKLib.DH_MSG_OBJECT();
/** 
检测到的车辆信息
*/
    public NetSDKLib.NET_MSG_OBJECT stuVehicle = new NetSDKLib.NET_MSG_OBJECT();
/** 
交通车辆信息
*/
    public NetSDKLib.DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stTrafficCar = new NetSDKLib.DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO();
    /**
     * 抓图标志(按位),具体见NET_RESERVED_COMMON
    */
    public int              dwSnapFlagMask;
    /**
     * 车身信息扩展,参见结构体定义 {@link NetSDKLib.NET_MSG_OBJECT_EX2}
    */
    public Pointer          pstuVehicleEx2;
/** 
保留字节
*/
    public			byte[]         byResersed = new byte[1020-NetSDKLib.POINTERSIZE];

public DEV_EVENT_TRAFFIC_CAR_MEASUREMENT_INFO(){
}
}

