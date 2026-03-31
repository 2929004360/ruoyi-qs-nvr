package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  湿度报警事件 
* @origin autoTool
* @date 2023/12/05 16:13:52
*/
public class ALARM_HUMIDITY_ALARM_INFO extends NetSDKLib.SdkStructure {
/** 
/ 通道号
*/
    public			int            nChannelID;
/** 
/ 事件动作,0表示脉冲事件,1表示事件开始,2表示事件结束;
*/
    public			int            nAction;
/** 
/ 时间戳(单位是毫秒)
*/
    public			double         dbPTS;
/** 
/ 事件名称
*/
    public			byte[]         szName = new byte[128];
/** 
/ 事件ID
*/
    public			int            nEventID;
/** 
/ 事件发生时间
*/
    public NET_TIME_EX      UTC = new NET_TIME_EX();
/** 
/ 当前湿度值
*/
    public			float          fCurrentValue;
/** 
/ 报警阈值
*/
    public			float          fLimitValue;
/** 
/ GPS信息
*/
    public NET_GPS_STATUS_INFO stuGPSStatus = new NET_GPS_STATUS_INFO();
/** 
/ 预留字节
*/
    public			byte[]         byReserved = new byte[1024];

public ALARM_HUMIDITY_ALARM_INFO(){
}
}

