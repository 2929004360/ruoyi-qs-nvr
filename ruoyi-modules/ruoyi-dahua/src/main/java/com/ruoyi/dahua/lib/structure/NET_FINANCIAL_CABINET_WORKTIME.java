package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  排班时间 
* @date 2022/08/10 13:54:51
*/
public class NET_FINANCIAL_CABINET_WORKTIME extends NetSDKLib.SdkStructure {
/** 
使能 不使能则不校验排班时间
*/
    public			int            bEnable;
/** 
排班起始时间
*/
    public			byte[]         szStartTime = new byte[16];
/** 
排班结束时间
*/
    public			byte[]         szEndTime = new byte[16];
/** 
星期天数
*/
    public			int            nWeekDayNum;
/** 
星期 0:周日，1:周一，…，6:周六
*/
    public			int[]          nWeekDay = new int[7];
/** 
预留字节
*/
    public			byte[]         szReserverd = new byte[252];

public			NET_FINANCIAL_CABINET_WORKTIME(){
}
}

