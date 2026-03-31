package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  获取输出状态 输入参数。此时CLIENT_GetAlarmRegionInfo的emType参数为NET_EM_GET_ALARMREGION_INFO_OUTPUTSTATE 
* @origin autoTool
* @date 2023/08/09 16:06:48
*/
public class NET_IN_GET_OUTPUT_STATE extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
通道类型 {@link com.ruoyi.dahua.lib.enumeration.EM_OUTPUT_TYPE}
*/
    public			int            emType;

public NET_IN_GET_OUTPUT_STATE(){
		this.dwSize=this.size();
}
}

