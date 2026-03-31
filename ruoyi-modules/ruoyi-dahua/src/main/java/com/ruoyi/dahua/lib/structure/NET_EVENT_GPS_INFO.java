package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  触发事件时物体的GPS信息 
* @date 2022/05/30 14:16:15
*/
public class NET_EVENT_GPS_INFO extends NetSDKLib.SdkStructure {
/** 
经度，单位：度,正为东经，负为西经，取值范围[-180,180]
*/
    public			double         dLongitude;
/** 
纬度，单位：度,正为北纬，负为南纬，取值范围[-90,90]
*/
    public			double         dLatitude;
/** 
预留字节
*/
    public			byte[]         bReserved = new byte[24];

public			NET_EVENT_GPS_INFO(){
}
}

