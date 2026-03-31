package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
事件类型 EVENT_IVS_BREAK_RULE_BUILDING_DETECTION （违章建筑检测事件）对应的数据块描述信息
*/
public class DEV_EVENT_BREAK_RULE_BUILDIING_DETECTION_INFO extends NetSDKLib.SdkStructure {
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
    public			double         PTS;
/** 
事件发生的时间
*/
    public NET_TIME_EX      UTC = new NET_TIME_EX();
/** 
智能事件所属大类
*/
    public			int            emClassType;
/** 
全景广角图(当前事件只有：nOffSet、nLength有效)
*/
    public NetSDKLib.SCENE_IMAGE_INFO stuSceneImageInfo = new NetSDKLib.SCENE_IMAGE_INFO();
/** 
事件ID
*/
    public			int            dwEventID;
/** 
字节对齐
*/
    public			byte[]         szreserved = new byte[4];
/** 
获取角度
*/
    public			NET_CAMERA_PTZ_INFO stuCameraPtz = new NET_CAMERA_PTZ_INFO();
/** 
目标矩形框
*/
    public NET_RECT         stuBoundingBox = new NET_RECT();
/** 
检测到的物体个数
*/
    public			int            nObjectNum;
/** 
检测到的物体信息
*/
    public			NetSDKLib.DH_MSG_OBJECT[] stuObjects = (NetSDKLib.DH_MSG_OBJECT[])new NetSDKLib.DH_MSG_OBJECT().toArray(128);
/** 
预留字段
*/
    public			byte[]         byReserved = new byte[888];
}

