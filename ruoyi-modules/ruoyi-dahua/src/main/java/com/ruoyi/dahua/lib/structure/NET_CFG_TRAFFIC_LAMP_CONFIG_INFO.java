package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  时间间隔配置, 对应 NET_EM_TRAFFIC_LAMP_CONFIG 
* @origin autoTool
* @date 2023/02/15 10:09:20
*/
public class NET_CFG_TRAFFIC_LAMP_CONFIG_INFO extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
DH_ALARM_TRAFFIC_LAMP_WORK_STATE报警事件的通知周期, 单位是秒, 默认600, 范围300~86400
*/
    public			int            nNotifyPeriod;

public NET_CFG_TRAFFIC_LAMP_CONFIG_INFO(){
		this.dwSize=this.size();
}
}

