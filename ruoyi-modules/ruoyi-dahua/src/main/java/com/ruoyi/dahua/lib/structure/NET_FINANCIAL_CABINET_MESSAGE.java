package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  短信推送 
* @date 2022/08/10 13:54:51
*/
public class NET_FINANCIAL_CABINET_MESSAGE extends NetSDKLib.SdkStructure {
/** 
使能
*/
    public			int            bEnable;
/** 
推送手机号个数
*/
    public			int            nPhoneNumberNum;
/** 
推送手机号
*/
    public			ByteSize16[]   szPhoneNumber = new ByteSize16[8];
/** 
预留字节
*/
    public			byte[]         szReserverd = new byte[256];

public			NET_FINANCIAL_CABINET_MESSAGE(){
}
}

