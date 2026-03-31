package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  回调的温湿度信息 
* @origin autoTool
* @date 2023/12/02 10:25:56
*/
public class NET_HYGROTHERMOGRAPH_INFO extends NetSDKLib.SdkStructure {
/** 
/ 温度, 单位: ℃
*/
    public			float          fTemperature;
/** 
/ 湿度  单位: %rh
*/
    public			float          fHumidity;
/** 
/ 上报通道号
*/
    public			int            nChannel;
/** 
/ 保留字节
*/
    public			byte[]         szResvered = new byte[1020];

public			NET_HYGROTHERMOGRAPH_INFO(){
}
}

