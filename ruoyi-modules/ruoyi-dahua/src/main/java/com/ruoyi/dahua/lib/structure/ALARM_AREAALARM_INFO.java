package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  区域报警事件(对应事件 DH_ALARM_AREAALARM) 
* @date 2022/07/19 19:31:27
*/
public class ALARM_AREAALARM_INFO extends NetSDKLib.SdkStructure {
/** 
区域号
*/
    public			int            nAreaIndex;
/** 
事件ID
*/
    public			int            nEventID;
/** 
事件发生的时间
*/
    public NET_TIME_EX      UTC = new NET_TIME_EX();
/** 
名称
*/
    public			byte[]         szName = new byte[128];
/** 
防区类型 {@link com.ruoyi.dahua.lib.enumeration.EM_DEFENCE_AREA_TYPE}
*/
    public			int            emDefenceAreaType;
/** 
触发的通道号
*/
    public			int            nIndex;
/** 
触发类型 {@link com.ruoyi.dahua.lib.enumeration.EM_AREAALARM_TRIGGER_TYPE}
*/
    public			int            emTrigerType;
/** 
保留扩展字节
*/
    public			byte[]         byReserved = new byte[1024];

public ALARM_AREAALARM_INFO(){
}
}

