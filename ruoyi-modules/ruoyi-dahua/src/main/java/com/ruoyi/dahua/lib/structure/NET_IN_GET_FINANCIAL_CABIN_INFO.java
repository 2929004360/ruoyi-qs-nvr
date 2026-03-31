package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_GetFinancialCabinInfo 输入参数 
* @date 2022/08/01 14:13:04
*/
public class NET_IN_GET_FINANCIAL_CABIN_INFO extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
舱位下标索引个数
*/
    public			int            nIndexNum;
/** 
舱位下标索引数组
*/
    public			int[]          nIndex = new int[64];

public NET_IN_GET_FINANCIAL_CABIN_INFO(){
		this.dwSize=this.size();
}
}

