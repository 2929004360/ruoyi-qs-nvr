package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  流量统计信息 
* @date 2022/08/22 17:27:50
*/
public class NET_WATERDATA_STAT_SERVER_INFO extends NetSDKLib.SdkStructure {
/** 
开始时间
*/
    public NET_TIME_EX      stuStartTime = new NET_TIME_EX();
/** 
水质类别 {@link com.ruoyi.dahua.lib.enumeration.EM_WATER_QUALITY}
*/
    public			int            emQuality;
/** 
水质检测上报数据信息
*/
    public NET_WATER_DETECTION_UPLOAD_INFO stuUploadInfo = new NET_WATER_DETECTION_UPLOAD_INFO();
/** 
预留字节
*/
    public			byte[]         szReserved = new byte[256];

public			NET_WATERDATA_STAT_SERVER_INFO(){
}
}

