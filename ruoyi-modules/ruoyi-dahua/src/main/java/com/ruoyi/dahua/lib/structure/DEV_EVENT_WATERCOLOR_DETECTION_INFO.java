package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
事件类型 EVENT_IVS_WATERCOLOR_DETECTION (水体颜色事件) 对应的数据描述信息
*/
public class DEV_EVENT_WATERCOLOR_DETECTION_INFO extends NetSDKLib.SdkStructure {
/** 
通道号
*/
    public			int            nChannelID;
/** 
事件动作,0表示脉冲事件,1表示持续性事件开始,2表示持续性事件结束;
*/
    public			int            nAction;
/** 
智能事件所属大类 EM_CLASS_TYPE
*/
    public			int            emClassType;
/** 
事件名称
*/
    public			byte[]         szName = new byte[128];
/** 
事件组ID
*/
    public			int            nGroupID;
/** 
一个事件组内应有的抓拍张数
*/
    public			int            nCountInGroup;
/** 
一个事件组内的抓拍序号
*/
    public			int            nIndexInGroup;
/** 
事件ID编号
*/
    public			int            nEventID;
/** 
时间戳(单位是毫秒)
*/
    public			double         PTS;
/** 
事件发生的时间(单位是秒)
*/
    public			int            UTC;
/** 
事件时间(单位是毫秒)
*/
    public			int            UTCMS;
/** 
实际检测到区域个数
*/
    public			int            nDetectRegionNum;
/** 
检测区域
*/
    public			NetSDKLib.DH_POINT[] stuDetectRegion = (NetSDKLib.DH_POINT[])new NetSDKLib.DH_POINT().toArray(20);
/** 
事件触发的预置点号，从1开始, 没有该字段，表示预置点未知
*/
    public			int            nPresetID;
/** 
污水颜色
*/
    public			int            emWaterColor;
/** 
原始图
*/
    public NetSDKLib.NET_INTELLIGENCE_IMAGE_INFO stuOriginalImage = new NetSDKLib.NET_INTELLIGENCE_IMAGE_INFO();
/** 
球机变到最小倍下的抓图
*/
    public NetSDKLib.NET_INTELLIGENCE_IMAGE_INFO stuSceneImage = new NetSDKLib.NET_INTELLIGENCE_IMAGE_INFO();
/** 
保留字节
*/
    public			byte[]         byReserved = new byte[1024];
}

