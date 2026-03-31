package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description CLIENT_SetConsumeResult 输入结构体
* @date 2022/03/07 17:36:06
*/
public class NET_IN_SET_CONSUME_RESULT extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
通道号
*/
    public			int            nChannelID;
/** 
用户ID
*/
    public			byte[]         szUserID = new byte[32];
/** 
消费后余额，如果消费失败则是消费前余额，单位分。
*/
    public			int            nRemainAmount;
/** 
消费成功与否
*/
    public			int            bConsumeResult;
/** 
错误码
*/
    public			int            nErrorCode;
/** 
平台固定消费金额，单位(分)
*/
    public			int            nConsumptionAmount;
/** 
是否是超额消费
*/
    public			int            bExcessConsumption;
/** 
剩余消费次数，-1表示不限制消费次数，0以上（包括0）表示剩余消费次数
*/
    public			int            nRemainingTimes;
/** 
消费流水号
*/
    public			byte[]         szTransactionSerialNumber = new byte[128];
    /**
     * PlatDisplayText
    */
    public byte[]           szPlatDisplayText = new byte[128];

public NET_IN_SET_CONSUME_RESULT(){
this.dwSize=this.size();
}
}

