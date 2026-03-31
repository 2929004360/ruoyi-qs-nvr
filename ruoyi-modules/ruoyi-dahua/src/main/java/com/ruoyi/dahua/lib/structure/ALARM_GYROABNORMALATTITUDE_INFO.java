package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  事件类型 NET_ALARM_GYROABNORMALATTITUDE
* @origin autoTool
* @date 2023/09/06 16:21:38
*/
public class ALARM_GYROABNORMALATTITUDE_INFO extends NetSDKLib.SdkStructure {
/** 
事件动作,1表示持续性事件开始,2表示持续性事件结束;
*/
    public			int            nAction;
/** 
通道号
*/
    public			int            nChannelID;
/** 
时间戳(单位是毫秒)
*/
    public			double         dbPTS;
/** 
事件发生时间
*/
    public NET_TIME_EX      stuTime = new NET_TIME_EX();
/** 
事件ID
*/
    public			int            nEventID;
/** 
当前翻滚角，单位：度
*/
    public			double         dbGyroCurRoll;
/** 
当前俯仰角，单位：度
*/
    public			double         dbGyroCurPitch;
/** 
当前加速度，单位：m/s^2
*/
    public			double         dbGyroCurAccel;
/** 
保留字段
*/
    public			byte[]         byReserved = new byte[1024];

public ALARM_GYROABNORMALATTITUDE_INFO(){
}
}

