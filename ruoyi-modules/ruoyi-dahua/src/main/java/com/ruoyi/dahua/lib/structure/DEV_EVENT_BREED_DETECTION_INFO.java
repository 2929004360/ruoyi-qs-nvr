package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description 事件类型EVENT_IVS_BREED_DETECTION(智慧养殖检测事件)对应的数据块描述信息
* @date 2022/04/15 15:29:30
*/
public class DEV_EVENT_BREED_DETECTION_INFO extends NetSDKLib.SdkStructure {
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
智能事件规则编号，用于标示哪个规则触发的事件
*/
    public			int            nRuleID;
/** 
帧序号
*/
    public			int            nSequence;
/** 
智能事件所属大类 {@link NetSDKLib.EM_CLASS_TYPE}
*/
    public			int            emClassType;
/** 
全景广角图
*/
    public NetSDKLib.SCENE_IMAGE_INFO_EX stuSceneImage = new NetSDKLib.SCENE_IMAGE_INFO_EX();
/** 
字节对齐
*/
    public			byte[]         byReserved1 = new byte[4];
/** 
检测到的物体个数
*/
    public			int            nObjectNum;
/** 
检测到的物体信息
*/
    public			NET_VAOBJECT_ANIMAL_INFO[] stuObjects = (NET_VAOBJECT_ANIMAL_INFO[])new NET_VAOBJECT_ANIMAL_INFO().toArray(64);
/** 
栏位温度（单位：℃）
*/
    public			double         dBreedStallTemp;
/** 
栏位号
*/
    public			int            nBreedStallNum;
/** 
预留字节
*/
    public			byte[]         byReserved = new byte[500];
}

