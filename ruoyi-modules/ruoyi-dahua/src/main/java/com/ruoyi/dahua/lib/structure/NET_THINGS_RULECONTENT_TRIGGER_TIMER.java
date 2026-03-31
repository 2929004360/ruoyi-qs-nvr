package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 260611
* @description  定时触发类型内容(对应EM_THINGS_TRIGGER_TYPE_TRIGGER_TIMER) 
* @date 2022/04/20 11:28:15
*/
public class NET_THINGS_RULECONTENT_TRIGGER_TIMER extends NetSDKLib.SdkStructure {
/** 
Cron时间格式
*/
    public			NET_THINGS_TRIGGER_TIMER_CORN stuCron = new NET_THINGS_TRIGGER_TIMER_CORN();
/** 
Corn表达式类 {@link com.ruoyi.dahua.lib.enumeration.EM_THINGS_TRIGGER_TIMER_CORN_TYPE}
*/
    public			int            emCronType;
/** 
时区，示例：Asia/Shanghai
*/
    public			byte[]         szTimezoneID = new byte[64];
/** 
保留字节
*/
    public			byte[]         szReserve = new byte[256];
}

