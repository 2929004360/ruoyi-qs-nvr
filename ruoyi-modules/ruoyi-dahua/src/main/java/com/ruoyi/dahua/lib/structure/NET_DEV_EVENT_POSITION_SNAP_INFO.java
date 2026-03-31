package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  事件类型 EVENT_IVS_POSITION_SNAP (按位置抓图事件)对应的数据块描述信息 
* @date 2022/11/15 09:31:15
*/
public class NET_DEV_EVENT_POSITION_SNAP_INFO extends NetSDKLib.SdkStructure {
/** 
通道号
*/
    public			int            nChannelID;
/** 
0:脉冲,1:开始, 2:停止
*/
    public			int            nAction;
/** 
事件发生的时间
*/
    public NET_TIME_EX      stuUTC = new NET_TIME_EX();
/** 
扩展协议字段
*/
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
/** 
按位置抓图的类型, 目前仅有"PresetSnap"
*/
    public			byte[]         szType = new byte[64];
/** 
预置点序号
*/
    public			int            nPresetID;
/** 
预置点名称
*/
    public			byte[]         szPresetName = new byte[64];
/** 
GPS信息
*/
    public NetSDKLib.NET_GPS_INFO stuGPSInfo = new NetSDKLib.NET_GPS_INFO();
/** 
云台方向与放大倍数，显示值，扩大100倍值, 该数组固定为3,第一个元素为水平角度，0-36000； 第二个元素为垂直角度，（-18000）-（18000）； 第三个元素为显示放大倍数，0-100。
*/
    public			int[]          nAbsposition = new int[3];
/** 
预留字节
*/
    public			byte[]         szReserved = new byte[1024];

public NET_DEV_EVENT_POSITION_SNAP_INFO(){
}
}

