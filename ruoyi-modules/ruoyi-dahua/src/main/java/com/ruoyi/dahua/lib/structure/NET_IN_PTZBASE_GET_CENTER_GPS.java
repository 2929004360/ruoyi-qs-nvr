package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  CLIENT_QueryDevInfo NET_QUERY_PTZBASE_GET_CENTER_GPS 类型接口输入参数 
* @date 2022/05/18 10:44:10
*/
public class NET_IN_PTZBASE_GET_CENTER_GPS extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
通道号从0开始
*/
    public			int            nChannel;
/** 
计算GPS信息标志位，为TRUE时使用dPosition中的位置信息来进行计算，为FALSE时使用当前云台PT位置信息计算GPS
*/
    public			int            bPosEnable;
/** 
云台方向信息，第一个元素为水平角度，第二个元素为垂直角度
*/
    public			double[]       dPosition = new double[2];

public NET_IN_PTZBASE_GET_CENTER_GPS(){
		this.dwSize=this.size();
}
}

