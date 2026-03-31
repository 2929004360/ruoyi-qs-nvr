package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  配件能力集 
* @date 2022/08/31 14:44:17
*/
public class NET_WPAN_ACCESSORY_CAPS_INFO extends NetSDKLib.SdkStructure {
/** 
是否支持报警声音设置
*/
    public			int            bSupportAlarmTone;
/** 
是否支持读卡器
*/
    public			int            bSupportCardReader;
/** 
是否支持门铃模式
*/
    public			int            bSupportChime;
/** 
是否支持超温报警
*/
    public			int            bSupportOverTemperatureAlarm;
/** 
是否支持外部wifi设置
*/
    public			int            bSupportExternalWifi;
/** 
是否支持wifi信息设置
*/
    public			int            bSupportWifiInfo;
/** 
预留字段
*/
    public			byte[]         byreserve = new byte[32];

public			NET_WPAN_ACCESSORY_CAPS_INFO(){
}
}

