package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description 用户信息
* @date 2022/03/08 11:34:32
*/
public class NET_ACCESS_USER_INFO_EX extends NetSDKLib.SdkStructure {
/** 
消费时间段
 // 每天最多6个时间段，每6个元素对应一天。一共7天。
 // 每个时段格式为"星期 时:分:秒-时:分:秒 消费类型 可消费次数 可消费金额"，
 // 星期从0开始，表示周日，前6个时段前面都是0，表示周日的6个时段，
 // 剩下依次周一，周二... 一共42个时段。消费类型包括：0为定额消费，
 // 1为非定额消费；可消费次数最大上限200次；可消费金额最高999900，也就是9999元
*/
    public			byte[]         szConsumptionTimeSections = new byte[42*34];
/** 
保留字节
*/
    public			byte[]         byReserved = new byte[1024];
}

