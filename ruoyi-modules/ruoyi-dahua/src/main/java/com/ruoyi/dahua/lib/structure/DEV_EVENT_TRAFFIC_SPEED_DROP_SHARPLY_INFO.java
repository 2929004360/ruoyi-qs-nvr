package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  事件类型 EVENT_IVS_TRAFFIC_SPEED_DROP_SHARPLY (车辆速度骤减)对应的数据块描述信息 
* @origin autoTool
* @date 2023/02/18 11:13:44
*/
public class DEV_EVENT_TRAFFIC_SPEED_DROP_SHARPLY_INFO extends NetSDKLib.SdkStructure {
/** 
通道号
*/
    public			int            nChannelID;
/** 
0:脉冲,1:开始, 2:停止
*/
    public			int            nAction;
/** 
事件名称
*/
    public			byte[]         szName = new byte[128];
/** 
事件时间毫秒数
*/
    public			int            nUTCMS;
/** 
事件发生的时间
*/
    public NET_TIME_EX      stuUTC = new NET_TIME_EX();
/** 
事件ID
*/
    public			int            nEventID;
/** 
相对事件时间戳,(单位是毫秒)
*/
    public			double         dbPTS;
/** 
事件组ID
*/
    public			int            nGroupID;
/** 
一个事件组内的抓拍张数
*/
    public			int            nCountInGroup;
/** 
一个事件组内的抓拍序号
*/
    public			int            nIndexInGroup;
/** 
车牌信息
*/
    public NetSDKLib.NET_MSG_OBJECT stuObject = new NetSDKLib.NET_MSG_OBJECT();
/** 
车辆信息
*/
    public NetSDKLib.NET_MSG_OBJECT stuVehicle = new NetSDKLib.NET_MSG_OBJECT();
/** 
非机动车信息
*/
    public NetSDKLib.VA_OBJECT_NONMOTOR stuNonMotor = new NetSDKLib.VA_OBJECT_NONMOTOR();
/** 
车道号
*/
    public			int            nLane;
/** 
抓包序号
*/
    public			int            nSequence;
/** 
公共信息
*/
    public NetSDKLib.EVENT_COMM_INFO stCommInfo = new NetSDKLib.EVENT_COMM_INFO();
/** 
交通车辆信息
*/
    public DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stTrafficCar = new DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO();
/** 
保留字节
*/
    public			byte[]         szReserved = new byte[1024];

public DEV_EVENT_TRAFFIC_SPEED_DROP_SHARPLY_INFO(){
}
}

