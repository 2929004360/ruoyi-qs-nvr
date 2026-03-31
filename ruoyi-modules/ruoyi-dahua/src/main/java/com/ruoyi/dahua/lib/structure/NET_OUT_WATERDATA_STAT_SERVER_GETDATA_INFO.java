package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_GetWaterDataStatServerWaterData 输出参数 
* @date 2022/08/22 16:51:44
*/
public class NET_OUT_WATERDATA_STAT_SERVER_GETDATA_INFO extends NetSDKLib.SdkStructure {
/** 
此结构体大小,必须赋值
*/
    public			int            dwSize;
/** 
水质类别，越小越好 {@link com.ruoyi.dahua.lib.enumeration.EM_WATER_QUALITY}
*/
    public			int            emQuality;
/** 
水质检测上报数据信息
*/
    public			NET_WATER_DETECTION_UPLOAD_INFO stuUploadInfo = new NET_WATER_DETECTION_UPLOAD_INFO();
/** 
参数超过阈值类型个数
*/
    public			int            nFlunkTypeNum;
/** 
参数超过阈值类型   {@link com.ruoyi.dahua.lib.enumeration.EM_WATER_DETECTION_ALARM_TYPE}
*/
    public			int[]          emFlunkType = new int[32];

public NET_OUT_WATERDATA_STAT_SERVER_GETDATA_INFO(){
		this.dwSize=this.size();
}
}

