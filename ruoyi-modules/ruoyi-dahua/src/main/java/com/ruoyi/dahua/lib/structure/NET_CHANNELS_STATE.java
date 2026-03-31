package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  通道状态
* @date 2022/10/09 11:24:36
*/
public class NET_CHANNELS_STATE extends NetSDKLib.SdkStructure {
/** 
通道类型 {@link com.ruoyi.dahua.lib.enumeration.EM_CHANNELS_STATE_TYPE}
*/
    public			int            emType;
/** 
通道号
*/
    public			int            nIndex;
/** 
在线状态 {@link com.ruoyi.dahua.lib.enumeration.EM_DEV_STATUS}
*/
    public			int            emOnlineState;
/** 
报警状态，当emType为EM_CHANNELS_STATE_TYPE_ALARMIN时有效 {@link com.ruoyi.dahua.lib.enumeration.EM_ZONE_STATUS}
*/
    public			int            emAlarmState;
/** 
输出状态，当emType为EM_CHANNELS_STATE_TYPE_ALARMOUT,EM_CHANNELS_STATE_TYPE_SIREN时有效 {@link com.ruoyi.dahua.lib.enumeration.EM_OUTPUT_STATE}
*/
    public			int            emOutputState;
/** 
通道对应名称
*/
    public			byte[]         szName = new byte[64];
/** 
通道对应SN号
*/
    public			byte[]         szSN = new byte[32];
/** 
探测器状态信息
*/
    public			NET_SENSOR_STATE stuSensorState = new NET_SENSOR_STATE();
/** 
保留字节
*/
    public			byte[]         byReserved = new byte[60];

public NET_CHANNELS_STATE(){
}
}

