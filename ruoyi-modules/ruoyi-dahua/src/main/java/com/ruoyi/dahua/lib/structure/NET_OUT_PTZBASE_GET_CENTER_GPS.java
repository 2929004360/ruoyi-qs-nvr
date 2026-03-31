package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_QueryDevInfo NET_QUERY_PTZBASE_GET_CENTER_GPS 类型接口输出参数 
* @date 2022/05/18 10:44:49
*/
public class NET_OUT_PTZBASE_GET_CENTER_GPS extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
经度，单位：度,正为东经，负为西经，取值范围[-180,180]
*/
    public			double         dLongitude;
/** 
纬度，单位：度,正为北纬，负为南纬，取值范围[-90,90]
*/
    public			double         dLatitude;

public NET_OUT_PTZBASE_GET_CENTER_GPS(){
		this.dwSize=this.size();
}
}

