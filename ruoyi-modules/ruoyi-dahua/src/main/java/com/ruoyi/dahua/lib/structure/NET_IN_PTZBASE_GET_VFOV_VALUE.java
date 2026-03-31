package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_QueryDevInfo NET_QUERY_PTZBASE_GET_VFOV_VALUE 类型接口输入参数 
* @date 2022/05/18 10:38:04
*/
public class NET_IN_PTZBASE_GET_VFOV_VALUE extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
通道号从0开始
*/
    public			int            nChannel;
/** 
镜头变倍值,归一化到0~1
*/
    public			float          fZoom;

public NET_IN_PTZBASE_GET_VFOV_VALUE(){
		this.dwSize=this.size();
}
}

