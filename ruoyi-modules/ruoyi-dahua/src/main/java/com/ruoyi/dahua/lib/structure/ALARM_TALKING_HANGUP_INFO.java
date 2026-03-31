package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  报警事件类型DH_ALARM_TALKING_HANGUP对应的数据描述信息 
* @origin autoTool
* @date 2023/06/19 15:36:49
*/
public class ALARM_TALKING_HANGUP_INFO extends NetSDKLib.SdkStructure {
    public			int            dwSize;
/** 
事件触发时间
*/
    public NET_TIME         stuTime = new NET_TIME();
/** 
挂断的房间号
*/
    public			byte[]         szRoomNo = new byte[128];
/** 
RealUTC 是否有效，bRealUTC 为 TRUE 时，用 RealUTC，否则用 stuTime 字段
*/
    public			int            bRealUTC;
/** 
事件发生的时间（标准UTC）
*/
    public NET_TIME_EX      RealUTC = new NET_TIME_EX();
/** 
语音挂断发起方
*/
    public			byte[]         szCaller = new byte[32];

public ALARM_TALKING_HANGUP_INFO(){
		this.dwSize=this.size();
}
}

