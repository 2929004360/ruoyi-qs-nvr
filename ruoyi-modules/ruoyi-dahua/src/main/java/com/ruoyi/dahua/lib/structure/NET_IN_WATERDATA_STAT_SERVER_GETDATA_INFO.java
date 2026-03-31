package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_GetWaterDataStatServerWaterData 输入参数 
* @date 2022/08/22 16:50:06
*/
public class NET_IN_WATERDATA_STAT_SERVER_GETDATA_INFO extends NetSDKLib.SdkStructure {
/** 
此结构体大小,必须赋值
*/
    public			int            dwSize;
/** 
检测类型个数
*/
    public			int            nTypeNum;
/** 
检测类型  {@link com.ruoyi.dahua.lib.enumeration.EM_WATER_DETECTION_ALARM_TYPE}
*/
    public			int[]          emType = new int[32];

public NET_IN_WATERDATA_STAT_SERVER_GETDATA_INFO(){
		this.dwSize=this.size();
}
}

