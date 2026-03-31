package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  存储容量不足事件 
* @origin autoTool
* @date 2023/09/06 15:55:03
*/
public class ALARM_STORAGE_LOW_SPACE_INFO extends NetSDKLib.SdkStructure {
    public			int            dwSize;
/** 
0:开始 1:停止
*/
    public			int            nAction;
/** 
事件名称
*/
    public			byte[]         szName = new byte[128];
/** 
存储设备名称
*/
    public			byte[]         szDevice = new byte[128];
/** 
存储组名称
*/
    public			byte[]         szGroup = new byte[128];
/** 
总容量, byte
*/
    public			long           nTotalSpace;
/** 
剩余容量, byte
*/
    public			long           nFreeSpace;
/** 
已经使用的百分比
*/
    public			int            nPercent;
/** 
事件触发时间
*/
    public NET_TIME_EX      stuTime = new NET_TIME_EX();
/** 
GPS信息
*/
    public			NET_GPS_STATUS_INFO stGPSStatus = new NET_GPS_STATUS_INFO();
/** 
事件公共扩展字段结构体
*/
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();

public ALARM_STORAGE_LOW_SPACE_INFO(){
		this.dwSize=this.size();
}
}

