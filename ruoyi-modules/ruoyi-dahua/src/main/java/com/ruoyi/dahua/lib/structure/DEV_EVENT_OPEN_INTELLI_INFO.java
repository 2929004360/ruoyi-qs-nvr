package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/** 
* @author 291189
* @description  事件类型 EVENT_IVS_OPEN_INTELLI (开放智能事件)对应的数据块描述信息 
* @date 2023/02/06 14:57:49
*/
public class DEV_EVENT_OPEN_INTELLI_INFO extends NetSDKLib.SdkStructure {
/** 
通道号
*/
    public			int            nChannelID;
/** 
0:脉冲,1:开始, 2:停止
*/
    public			int            nAction;
/** 
所属开放算法的Id
*/
    public			byte[]         szOpenCode = new byte[32];
/** 
所属开放算法的名称
*/
    public			byte[]         szOpenName = new byte[128];
/** 
所属开放算法的规则类型, 仅支持: 拌线入侵CrossLineDetection(EVENT_IVS_CROSSLINEDETECTION)、区域入侵CrossRegionDetection(EVENT_IVS_CROSSREGIONDETECTION)、滞留检测StayDetection(EVENT_IVS_STAYDETECTION)、数量统计ObjectNumDetection(EVENT_IVS_OBJECT_NUM_DETECTION)、通用姿态行为事件 GenealAttitude(EVENT_IVS_GENEAL_ATTITUDE)
*/
    public			byte[]         szRuleType = new byte[32];
/** 
与开放算法的规则类型支持的带图事件类型对应的结构体对应(只解析Event Data中的字段)
拌线入侵CrossLineDetection(EVENT_IVS_CROSSLINEDETECTION) - DEV_EVENT_CROSSLINE_INFO
区域入侵CrossRegionDetection(EVENT_IVS_CROSSREGIONDETECTION) - DEV_EVENT_CROSSREGION_INFO
滞留检测StayDetection(EVENT_IVS_STAYDETECTION) - DEV_EVENT_STAY_INFO
数量统计ObjectNumDetection(EVENT_IVS_OBJECT_NUM_DETECTION) - DEV_EVENT_OBJECT_NUM_DETECTION_INFO
绊线计数事件 CrossLineStat(EVENT_IVS_CROSSLINE_STAT) - NET_DEV_EVENT_CROSSLINE_STAT_INFO
域内计数事件 RegionNumStat(EVENT_IVS_REGIONNUM_STAT) - NET_DEV_EVENT_REGIONNUM_STAT_INFO
全物体类型检测事件 AnythingDetect(EVENT_IVS_ANYTHING_DETECT) - DEV_EVENT_ANYTHING_DETECT_INFO
通用姿态行为事件 GenealAttitude(EVENT_IVS_GENEAL_ATTITUDE) - DEV_EVENT_GENEAL_ATTITUDE_INFO
*/
    public Pointer          pstuOpenData;
/** 
检测到的目标属性信息列表的个数
*/
    public			int            nObjectNums;
/** 
检测到的目标属性信息列表
*/
    public			NET_OPEN_INTELLI_OBJECT_INFO[] stuObjects = new NET_OPEN_INTELLI_OBJECT_INFO[100];
/** 
用户数据
*/
    public			NET_OPEN_INTELLI_USER_DATA_INFO stuUserData = new NET_OPEN_INTELLI_USER_DATA_INFO();
/** 
保留字节
*/
    public			byte[]         szReserved = new byte[1024];

public DEV_EVENT_OPEN_INTELLI_INFO(){
		for(int i=0;i<stuObjects.length;i++){
			stuObjects[i]=new NET_OPEN_INTELLI_OBJECT_INFO();
			}
}
}

