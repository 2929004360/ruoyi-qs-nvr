package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  事件类型EVENT_IVS_TRAFFIC_CHANGE_LANE_CONTINUES (机动车连续变道违法事件)对应的数据块描述信息 
* @origin autoTool
* @date 2023/09/07 10:53:35
*/
public class DEV_EVENT_TRAFFIC_CHANGE_LANE_CONTINUES_INFO extends NetSDKLib.SdkStructure {
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
    public			double         dbPTS;
/** 
事件发生的时间
*/
    public NET_TIME_EX      stuUTC = new NET_TIME_EX();
/** 
事件ID
*/
    public			int            nEventID;
/** 
nGroupID事件组ID,同一物体抓拍过程内nGroupID相同
*/
    public			int            nGroupID;
/** 
nCountInGroup一个事件组内的抓拍张数
*/
    public			int            nCountInGroup;
/** 
IndexInGroup一个事件组内的抓拍序号
*/
    public			int            nIndexInGroup;
/** 
事件类型掩码, bit0表示报警事件, bit1表示违章事件. 若bit0和bit1都置位则既是报警事件又是违章事件, 无该字段默认该事件是报警事件
*/
    public			int            nEventType;
/** 
车牌信息
*/
    public NetSDKLib.NET_MSG_OBJECT stuObject = new NetSDKLib.NET_MSG_OBJECT();
/** 
车身信息
*/
    public	NetSDKLib.NET_MSG_OBJECT stuVehicle = new NetSDKLib.NET_MSG_OBJECT();
/** 
TriggerType:触发类型,0车检器,1雷达,2视频 {@link com.ruoyi.dahua.lib.enumeration.EM_TRIGGER_TYPE}
*/
    public			int            emTriggerType;
/** 
底层产生的触发抓拍帧标记
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
对应车道号
*/
    public			int            nLane;
/** 
表示抓拍序号,如3,2,1,1表示抓拍结束,0表示异常结束
*/
    public			int            nSequence;
/** 
车速，单位km/h
*/
    public			int            nSpeed;
/** 
交通车辆信息
*/
    public DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stuTrafficCar = new DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO();
/** 
公共信息
*/
    public NetSDKLib.EVENT_COMM_INFO stuCommInfo = new NetSDKLib.EVENT_COMM_INFO();
/** 
GPS信息
*/
    public NetSDKLib.NET_GPS_INFO stuGPSInfo = new NetSDKLib.NET_GPS_INFO();
/** 
事件对应文件信息
*/
    public NetSDKLib.NET_EVENT_FILE_INFO stuFileInfo = new NetSDKLib.NET_EVENT_FILE_INFO();
/** 
抓图标志(按位),具体见NET_RESERVED_COMMON
*/
    public			int            dwSnapFlagMask;
/** 
保留字节,留待扩展
*/
    public			byte[]         szReserved = new byte[1024];

public DEV_EVENT_TRAFFIC_CHANGE_LANE_CONTINUES_INFO(){
}
}

