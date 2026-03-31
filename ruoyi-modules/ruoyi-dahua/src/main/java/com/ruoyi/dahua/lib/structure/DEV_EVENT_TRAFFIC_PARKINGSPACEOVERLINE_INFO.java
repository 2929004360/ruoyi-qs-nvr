 package com.ruoyi.dahua.lib.structure;


 import com.ruoyi.dahua.lib.NetSDKLib;
 /**
  * @author 291189
  * @version 1.0
  * @description 事件类型 EVENT_IVS_TRAFFIC_PARKINGSPACEOVERLINE (车位压线事件)对应的数据块描述信息

  * @date 2021/10/26
  */
public class DEV_EVENT_TRAFFIC_PARKINGSPACEOVERLINE_INFO extends NetSDKLib.SdkStructure {
  /** 通道号*/
    public			int            nChannelID;
  /** 事件名称*/
    public			byte[]         szName = new byte[128];
  /** 时间戳(单位是毫秒)*/
    public			int            PTS;
  /** 事件发生的时间*/
    public			NET_TIME_EX    UTC = new NET_TIME_EX();
  /** 事件ID*/
    public			int            nEventID;
  /** 对应车道号*/
    public			int            nLane;
  /** 检测到的物体*/
    public NetSDKLib.NET_MSG_OBJECT stuObject = new NetSDKLib.NET_MSG_OBJECT();
  /** 车身信息*/
    public NetSDKLib.NET_MSG_OBJECT stuVehicle = new NetSDKLib.NET_MSG_OBJECT();
  /** 事件对应文件信息*/
    public NetSDKLib.NET_EVENT_FILE_INFO stuFileInfo = new NetSDKLib.NET_EVENT_FILE_INFO();
  /** 表示抓拍序号,如3,2,1,1表示抓拍结束,0表示异常结束*/
    public			int            nSequence;
  /** 事件动作,0表示脉冲事件,1表示持续性事件开始,2表示持续性事件结束;*/
    public			byte           byEventAction;
  /** 图片的序号, 同一时间内(精确到秒)可能有多张图片, 从0开始*/
    public			byte           byImageIndex;
  /** */
    public			byte[]         byReserved1 = new byte[2];
  /** 抓图标志(按位),具体见NET_RESERVED_COMMON*/
    public			int            dwSnapFlagMask;
  /** 对应图片的分辨率*/
    public NetSDKLib.NET_RESOLUTION_INFO stuResolution = new NetSDKLib.NET_RESOLUTION_INFO();
  /** 交通车辆信息*/
    public NetSDKLib.DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stuTrafficCar = new NetSDKLib.DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO();
  /** 停车场信息*/
    public NetSDKLib.DEV_TRAFFIC_PARKING_INFO stTrafficParingInfo = new NetSDKLib.DEV_TRAFFIC_PARKING_INFO();
  /** 保留字节*/
    public			byte[]         byReserved = new byte[384];
  /** 公共信息*/
    public NetSDKLib.EVENT_COMM_INFO stCommInfo = new NetSDKLib.EVENT_COMM_INFO();
  /** 事件公共扩展字段结构体*/
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
}

