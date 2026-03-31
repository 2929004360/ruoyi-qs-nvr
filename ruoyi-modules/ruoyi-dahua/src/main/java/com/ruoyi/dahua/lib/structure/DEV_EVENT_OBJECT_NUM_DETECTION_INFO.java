package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  事件类型 EVENT_IVS_OBJECT_NUM_DETECTION (区域内计数事件)对应的数据块描述信息( 注意该事件不支持单独使用，仅配合“开放智能事件(EVENT_IVS_OPEN_INTELLI)”使用) 
* @date 2023/02/06 15:58:41
*/
public class DEV_EVENT_OBJECT_NUM_DETECTION_INFO extends NetSDKLib.SdkStructure {
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
事件ID
*/
    public			int            nEventID;
/** 
区域ID, 没有该字段或者为0，为单区域
*/
    public			int            nAreaID;
/** 
事件触发的预置点号，从1开始
*/
    public			int            nPresetID;
/** 
表示区域内物体的个数
*/
    public			int            nNumber;
/** 
全景广角图(仅nIndexInData, nOffSet, nLength字段有效)
*/
    public NetSDKLib.SCENE_IMAGE_INFO_EX stuSceneImage = new NetSDKLib.SCENE_IMAGE_INFO_EX();
/** 
预留字节
*/
    public			byte[]         szReserved = new byte[1024];

public DEV_EVENT_OBJECT_NUM_DETECTION_INFO(){
}
}

