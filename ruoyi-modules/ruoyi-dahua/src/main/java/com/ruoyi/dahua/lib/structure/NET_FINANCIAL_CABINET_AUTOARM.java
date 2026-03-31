package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  自动布撤防 
* @date 2022/08/10 13:54:51
*/
public class NET_FINANCIAL_CABINET_AUTOARM extends NetSDKLib.SdkStructure {
/** 
使能
*/
    public			int            bEnable;
/** 
撤防开始时间（自动布防结束时间）
*/
    public			byte[]         szDisarmStartTime = new byte[16];
/** 
撤防结束时间（自动布防开始时间）
*/
    public			byte[]         szDisarmEndTime = new byte[16];
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

public			NET_FINANCIAL_CABINET_AUTOARM(){
}
}

