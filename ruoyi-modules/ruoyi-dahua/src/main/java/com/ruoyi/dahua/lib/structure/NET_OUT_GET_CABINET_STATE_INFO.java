package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description   CLIENT_GetFinancialCabinetState 的输出参数 
* @date 2022/08/10 13:39:22
*/
public class NET_OUT_GET_CABINET_STATE_INFO extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
柜体温度，单位摄氏度
*/
    public			float          fTemperature;
/** 
柜体湿度，单位百分比
*/
    public			float          fHumidity;

public NET_OUT_GET_CABINET_STATE_INFO(){
		this.dwSize=this.size();
}
}

