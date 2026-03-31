package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  舱位信息 
* @date 2022/08/01 14:13:48
*/
public class NET_FINANCIAL_CABIN_INFO extends NetSDKLib.SdkStructure {
/** 
舱位读取到尾箱RFID卡编号
*/
    public			byte[]         szRFIDNo = new byte[128];
/** 
舱位绑定的尾箱RFID卡编号,为空时表示未绑定尾箱
*/
    public			byte[]         szBindRFIDNo = new byte[128];
/** 
舱位类型，"Cash"-现金柜，"Bill"-票据柜
*/
    public			byte[]         szType = new byte[32];
/** 
舱位门状态，"IsClosed"-关门，"IsOpened"-开门
*/
    public			byte[]         szDoorState = new byte[32];
/** 
舱位现金金额，尾箱类型为"Cash"时有效， 单位：人民币
*/
    public			float          fAmount;
/** 
预留字节
*/
    public			byte[]         szReserved = new byte[124];

public NET_FINANCIAL_CABIN_INFO(){
}
}

