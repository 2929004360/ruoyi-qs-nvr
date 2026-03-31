package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  事件类型 EVENT_IVS_TRAFFIC_TURN_SHARP (急转弯事件)对应的数据块描述信息 
* @origin autoTool
* @date 2023/02/14 20:27:40
*/
public class NET_DEV_EVENT_TRAFFIC_TURN_SHARP_INFO extends NetSDKLib.SdkStructure {
/** 
通道号
*/
    public			int            nChannelID;
/** 
0:脉冲,1:开始, 2:停止
*/
    public			int            nAction;
/** 
扩展协议字段
*/
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
/** 
事件发生的时间
*/
    public NET_TIME_EX      stuUTC = new NET_TIME_EX();
/** 
事件编号，用来唯一标志一个事件
*/
    public			int            nEventID;
/** 
事件名称
*/
    public			byte[]         szName = new byte[128];
/** 
事件组ID，同一辆车抓拍过程内GroupID相同。
*/
    public			int            nGroupID;
/** 
CountInGroup一个事件组内应有的抓拍张数
*/
    public			int            nCountInGroup;
/** 
一个事件组内的抓拍序号，从1开始
*/
    public			int            nIndexInGroup;
/** 
表示对应车道号
*/
    public			int            nLane;
/** 
相对事件时间戳,单位毫秒
*/
    public			double         dbPTS;
/** 
车辆信息
*/
    public NetSDKLib.NET_MSG_OBJECT stuVehicle = new NetSDKLib.NET_MSG_OBJECT();
/** 
车牌信息
*/
    public NetSDKLib.NET_MSG_OBJECT stuObject = new NetSDKLib.NET_MSG_OBJECT();
/** 
表示交通车辆的数据库记录
*/
    public DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stuTrafficCar = new DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO();
/** 
公共信息
*/
    public NetSDKLib.EVENT_COMM_INFO stuCommInfo = new NetSDKLib.EVENT_COMM_INFO();
/** 
非机动车信息
*/
    public NetSDKLib.VA_OBJECT_NONMOTOR stuNonMotor = new NetSDKLib.VA_OBJECT_NONMOTOR();
/** 
是否有非机动车对象
*/
    public			int            bHasNonMotor;
/** 
抓拍序号，如/0，1表示抓拍正常结束，0表示抓拍异常结束
*/
    public			int            nSequence;
/** 
车速，单位km/h
*/
    public			int            nSpeed;
/** 
触发类型
*/
    public			int            nTriggerType;
/** 
用于标记抓拍帧
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
预留字节
*/
    public			byte[]         szReserved = new byte[1020];

public NET_DEV_EVENT_TRAFFIC_TURN_SHARP_INFO(){
}
}

