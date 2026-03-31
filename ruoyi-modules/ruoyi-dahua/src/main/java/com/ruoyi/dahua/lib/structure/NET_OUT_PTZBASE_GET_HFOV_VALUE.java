package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_QueryDevInfo NET_QUERY_PTZBASE_GET_HFOV_VALUE 类型接口输出参数 
* @date 2022/05/18 10:25:12
*/
public class NET_OUT_PTZBASE_GET_HFOV_VALUE extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
对应倍率水平视场角,单位0.01度，扩大100倍表示
*/
    public			int            nValue;
/** 
镜头最小水平视场角,单位同nValue
*/
    public			int            nMinValue;
/** 
镜头最大水平视场角,单位同nValue
*/
    public			int            nMaxValue;

public NET_OUT_PTZBASE_GET_HFOV_VALUE(){
		this.dwSize=this.size();
}
}

