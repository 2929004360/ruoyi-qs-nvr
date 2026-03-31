package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  事件类型EVENT_IVS_ANTI_COUNTERFEIT(防造假检测事件)对应的数据块描述信息 
* @date 2022/08/24 15:23:14
*/
public class NET_DEV_EVENT_ANTI_COUNTERFEIT_INFO extends NetSDKLib.SdkStructure {
/** 
通道号
*/
    public			int            nChannelID;
/** 
0:脉冲 1:开始 2:停止
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
智能事件规则编号
*/
    public			int            nRuleID;
/** 
GroupID事件组ID
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
时间戳(单位是毫秒)
*/
    public			double         PTS;
/** 
事件发生的时间
*/
    public NET_TIME_EX      UTC = new NET_TIME_EX();
/** 
事件时间毫秒数
*/
    public			int            nUTCMS;
/** 
事件ID
*/
    public			int            nEventID;
/** 
帧序号
*/
    public			int            nSequence;
/** 
视频分析帧序号
*/
    public			int            nFrameSequence;
/** 
规则区域个数
*/
    public			int            nDetectRegionNum;
/** 
规则区域
*/
    public			NET_UINT_POINT[] stuDetectRegion = new NET_UINT_POINT[20];
/** 
Direction表示入侵方向, 参见警戒区配置
*/
    public			byte[]         szDirection = new byte[16];
/** 
Action表示动作，参见警戒区配置
*/
    public			byte[]         szAction = new byte[16];
/** 
全景广角图
*/
    public NetSDKLib.SCENE_IMAGE_INFO stuSceneImage = new NetSDKLib.SCENE_IMAGE_INFO();
/** 
检测到的物体信息
*/
    public NetSDKLib.NET_MSG_OBJECT stuObject = new NetSDKLib.NET_MSG_OBJECT();
/** 
用于标记抓拍帧
*/
    public			int            nMark;
/** 
视频分析的数据源地址
*/
    public			int            nSource;
/** 
事件序列号
*/
    public			int            nEventSeq;
/** 
预留字段
*/
    public			byte[]         szReserved = new byte[1020];

public NET_DEV_EVENT_ANTI_COUNTERFEIT_INFO(){
		for(int i=0;i<stuDetectRegion.length;i++){
			stuDetectRegion[i]=new NET_UINT_POINT();
			}
}
}

