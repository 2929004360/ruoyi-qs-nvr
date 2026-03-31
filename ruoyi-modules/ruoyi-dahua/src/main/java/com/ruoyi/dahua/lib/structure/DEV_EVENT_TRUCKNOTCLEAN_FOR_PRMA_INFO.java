package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  事件类型 EVENT_IVS_TRUCKNOTCLEAN_FOR_PRMA 工程车未清洗 对应的数据块描述信息 
* @date 2022/12/20 14:23:14
*/
public class DEV_EVENT_TRUCKNOTCLEAN_FOR_PRMA_INFO extends NetSDKLib.SdkStructure {
/** 
通道号
*/
    public			int            nChannelID;
/** 
0-脉冲 1-开始 2-结束
*/
    public			int            nAction;
/** 
事件名称
*/
    public			byte[]         szName = new byte[128];
/** 
时间戳(单位:毫秒)
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
事件对应文件信息
*/
    public NetSDKLib.NET_EVENT_FILE_INFO stuFileInfo = new NetSDKLib.NET_EVENT_FILE_INFO();
/** 
智能事件所属大类 {@link com.ruoyi.dahua.lib.enumeration.EM_CLASS_TYPE}
*/
    public			int            emClass;
/** 
帧序号
*/
    public			int            nSequence;
/** 
检测到的车牌信息
*/
    public NetSDKLib.NET_MSG_OBJECT stuObject = new NetSDKLib.NET_MSG_OBJECT();
/** 
stuSceneImage 是否有效
*/
    public			int            bSceneImage;
/** 
全景广角图
*/
    public NetSDKLib.SCENE_IMAGE_INFO_EX stuSceneImage = new NetSDKLib.SCENE_IMAGE_INFO_EX();
/** 
保留字节
*/
    public			byte[]         bReserved = new byte[1024];

public DEV_EVENT_TRUCKNOTCLEAN_FOR_PRMA_INFO(){
}
}

