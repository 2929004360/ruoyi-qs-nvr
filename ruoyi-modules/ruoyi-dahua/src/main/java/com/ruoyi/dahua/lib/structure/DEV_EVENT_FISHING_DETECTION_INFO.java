package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
事件类型 EVENT_IVS_FISHING_DETECTION (钓鱼检测事件)对应的数据块描述信息
*/
public class DEV_EVENT_FISHING_DETECTION_INFO extends NetSDKLib.SdkStructure {
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
时间戳(单位是毫秒)
*/
    public			double         dbPTS;
/** 
事件发生的时间
*/
    public NET_TIME_EX      stuUTC = new NET_TIME_EX();
/** 
事件ID 协议是uint32
*/
    public			int            nEventID;
/** 
智能事件所属大类
*/
    public			byte[]         szClass = new byte[16];
/** 
GroupID事件组ID，同一物体抓拍过程内GroupID相同
*/
    public			int            nGroupID;
/** 
CountInGroup一个事件组内的抓拍张数
*/
    public			int            nCountInGroup;
/** 
IndexInGroup一个事件组内的抓拍序号，从1开始
*/
    public			int            nIndexInGroup;
/** 
事件时间毫秒数
*/
    public			int            nUTCMS;
/** 
检测目标物体的个数
*/
    public			int            nObjectCount;
/** 
检测目标的物体信息
*/
    public			NetSDKLib.NET_MSG_OBJECT_EX2[] stuObjects = new NetSDKLib.NET_MSG_OBJECT_EX2[100];
/** 
检测区个数
*/
    public			int            nDetectRegionCount;
/** 
表示配置的规则区域
*/
    public			NetSDKLib.NET_POINT[] stuDetectRegion = (NetSDKLib.NET_POINT[])new NetSDKLib.NET_POINT().toArray(20);
/** 
事件触发的预置点号，从1开始
*/
    public			int            nPresetID;
/** 
全景广角图
*/
    public NetSDKLib.SCENE_IMAGE_INFO_EX stuSceneImage = new NetSDKLib.SCENE_IMAGE_INFO_EX();
/** 
预留字节
*/
    public			byte[]         szReserved = new byte[1020];

        public DEV_EVENT_FISHING_DETECTION_INFO(){

            for(int i=0;i<stuObjects.length;i++){
                stuObjects[i]=new NetSDKLib.NET_MSG_OBJECT_EX2();
            }

        }
}

