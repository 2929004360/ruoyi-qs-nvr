package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  事件类型 EVENT_IVS_TRAFFIC_ELETAGINFO(RFID电子车牌标签事件)对应数据块描述信息 
* @date 2022/10/13 14:39:18
*/
public class DEV_EVENT_TRAFFIC_ELETAGINFO_INFO extends NetSDKLib.SdkStructure {
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
0:脉冲 1:开始 2:停止
*/
    public			int            nAction;
/** 
RFID电子车牌标签信息
*/
    public NET_RFIDELETAG_INFO stuRFIDEleTagInfo = new NET_RFIDELETAG_INFO();
/** 
返回通道数
*/
    public			int            nRetChannelNum;
/** 
关联的相机通道,通道号从0开始
*/
    public			int[]          nLinkChannel = new int[16];
/** 
保留字节,留待扩展.
*/
    public			byte[]         bReserved = new byte[956];

public			DEV_EVENT_TRAFFIC_ELETAGINFO_INFO(){
}
}

