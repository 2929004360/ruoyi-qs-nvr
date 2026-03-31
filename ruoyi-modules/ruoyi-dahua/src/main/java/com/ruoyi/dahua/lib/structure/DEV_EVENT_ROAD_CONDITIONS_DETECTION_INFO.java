package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  事件类型 EVENT_IVS_ROAD_CONDITIONS_DETECTION (路面检测事件)对应的数据块描述信息 
* @origin autoTool
* @date 2023/06/16 10:02:53
*/
public class DEV_EVENT_ROAD_CONDITIONS_DETECTION_INFO extends NetSDKLib.SdkStructure {
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
智能事件所属大类
*/
    public			byte[]         szClass = new byte[16];
/** 
相对事件时间戳,(单位是毫秒)
*/
    public			double         dbPTS;
/** 
事件发生的时间
*/
    public NET_TIME_EX      stuUTC = new NET_TIME_EX();
/** 
事件时间毫秒数
*/
    public			int            nUTCMS;
/** 
路面状况等级 {@link com.ruoyi.dahua.lib.enumeration.EM_ROAD_LEVEL}
*/
    public			int            emRoadLevel;
/** 
GPS信息
*/
    public NetSDKLib.NET_GPS_STATUS_INFO stuGPSStatus = new NetSDKLib.NET_GPS_STATUS_INFO();
/** 
违章关联视频FTP上传路径
*/
    public			byte[]         szVideoPath = new byte[256];
/** 
路面病害面积，单位：平方米
*/
    public			float          fDamageArea;
/** 
路面病害长度，单位：米
*/
    public			float          fDamageLength;
/** 
桩号ID
*/
    public			int            nPileID;
/** 
临近桩号距离，单位米，支持正负值
*/
    public			int            nPileDistance;
/** 
桩号名称
*/
    public			byte[]         szPileName = new byte[128];
/** 
路线名称
*/
    public			byte[]         szLineName = new byte[128];
/** 
路面属性(如，"水泥路"，"柏油")
*/
    public			byte[]         szRoadAttribute = new byte[128];
/** 
预留字节
*/
    public			byte[]         szReserved = new byte[368];

public DEV_EVENT_ROAD_CONDITIONS_DETECTION_INFO(){
}
}

