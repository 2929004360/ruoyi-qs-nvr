package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 260611
* @description  定时触发类型Cron内容 
* @date 2022/04/20 11:28:15
*/
public class NET_THINGS_TRIGGER_TIMER_CORN extends NetSDKLib.SdkStructure {
/** 
秒，值：0~59的整数以及特殊4个字符, - * /
*/
    public			byte[]         szSeconds = new byte[8];
/** 
分，值：0~59的整数以及特殊4个字符, - * /
*/
    public			byte[]         szMinutes = new byte[8];
/** 
时，值：0~23的整数以及特殊4个字符, - * /
*/
    public			byte[]         szHours = new byte[8];
/** 
日期，值：1~31的整数（但是你需要考虑你月的天数）以及特殊8个字符, - * ? / L W C
*/
    public			byte[]         szDayofMonth = new byte[8];
/** 
月份，值：1~12的整数或者 JAN-DEC以及特殊4个字符, - * /
*/
    public			byte[]         szMonth = new byte[8];
/** 
星期，值：1~7的整数或者 SUN-SAT以及特殊8个字符, - * ? / L W C
*/
    public			byte[]         szDayofWeek = new byte[8];
/** 
年，值：1970~2099的整数以及特殊4个字符, - * /
*/
    public			byte[]         szYear = new byte[8];
/** 
保留字节
*/
    public			byte[]         szReserve = new byte[256];
}

