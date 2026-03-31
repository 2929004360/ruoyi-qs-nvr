package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description 事件类型EVENT_IVS_PRESERVATION(物品保全事件)对应的数据块描述信息
* @date 2022/04/07 19:26:34
*/
public class DEV_EVENT_PRESERVATION_INFO extends NetSDKLib.SdkStructure {
/** 
通道号
*/
    public			int            nChannelID;
/** 
事件名称
*/
    public			byte[]         szName = new byte[128];
/** 
字节对齐
*/
    public			byte[]         bReserved1 = new byte[4];
/** 
时间戳(单位是毫秒)
*/
    public			double         PTS;
/** 
事件发生的时间
*/
    public NET_TIME_EX      UTC = new NET_TIME_EX();
/** 
事件ID
*/
    public			int            nEventID;
/** 
检测到的物体
*/
    public NetSDKLib.DH_MSG_OBJECT stuObject = new NetSDKLib.DH_MSG_OBJECT();
/** 
事件对应文件信息
*/
    public NetSDKLib.NET_EVENT_FILE_INFO stuFileInfo = new NetSDKLib.NET_EVENT_FILE_INFO();
/** 
事件动作,0表示脉冲事件,1表示持续性事件开始,2表示持续性事件结束;
*/
    public			byte           bEventAction;
/** 

*/
    public			byte[]         byReserved = new byte[2];
/** 
图片的序号, 同一时间内(精确到秒)可能有多张图片, 从0开始
*/
    public			byte           byImageIndex;
/** 
规则检测区域顶点数
*/
    public			int            nDetectRegionNum;
/** 
规则检测区域
*/
    public			NetSDKLib.DH_POINT[] DetectRegion = (NetSDKLib.DH_POINT[])new NetSDKLib.DH_POINT().toArray(20);
/** 
抓图标志(按位),具体见NET_RESERVED_COMMON
*/
    public			int            dwSnapFlagMask;
/** 
事件源设备上的index,-1表示数据无效
*/
    public			int            nSourceIndex;
/** 
事件源设备唯一标识,字段不存在或者为空表示本地设备
*/
    public			byte[]         szSourceDevice = new byte[260];
/** 
事件触发累计次数
*/
    public			int            nOccurrenceCount;
/** 
智能事件公共信息
*/
    public NetSDKLib.EVENT_INTELLI_COMM_INFO stuIntelliCommInfo = new NetSDKLib.EVENT_INTELLI_COMM_INFO();
/** 
广角图
*/
    public NetSDKLib.SCENE_IMAGE_INFO stuSceneImage = new NetSDKLib.SCENE_IMAGE_INFO();
/** 
检测目标物体的个数
*/
    public			int            nObjectCount;
/** 
表示检测到的物体信息 有Objects, 以Objects为准，无Objects, 以Object为准，为兼容老的客户端，有Objects的，Object也要填数据
*/
    public			NetSDKLib.NET_MSG_OBJECT_EX2[] stuObjects = (NetSDKLib.NET_MSG_OBJECT_EX2[])new NetSDKLib.NET_MSG_OBJECT_EX2().toArray(128);
/** 
保留字节,留待扩展.
*/
    public			byte[]         szReserved = new byte[1024];
}

