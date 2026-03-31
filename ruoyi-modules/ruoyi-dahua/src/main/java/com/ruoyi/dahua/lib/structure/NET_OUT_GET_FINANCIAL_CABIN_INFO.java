package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_GetFinancialCabinInfo 输出参数 
* @date 2022/08/01 14:13:49
*/
public class NET_OUT_GET_FINANCIAL_CABIN_INFO extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
舱位信息个数
*/
    public			int            nInfoNum;
/** 
舱位信息,是数组和Index数组下标对应
*/
    public			NET_FINANCIAL_CABIN_INFO[] stuInfo = new NET_FINANCIAL_CABIN_INFO[64];

public NET_OUT_GET_FINANCIAL_CABIN_INFO(){
		for(int i=0;i<stuInfo.length;i++){
			stuInfo[i]=new NET_FINANCIAL_CABIN_INFO();
			}
		this.dwSize=this.size();
}
}

