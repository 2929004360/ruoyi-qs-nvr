package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  探测器状态信息 
* @date 2022/10/09 11:29:43
*/
public class NET_SENSOR_STATE extends NetSDKLib.SdkStructure {
/** 
外接电源连接状态 : 0:正常, 1:未连接
*/
    public			int            nExPowerState;
/** 
配件防拆状态 : 0:正常, 1:打开
*/
    public			int            nTamper;
/** 
电池电量状态 : 0:正常, 1:低电量, 2:掉电
*/
    public			int            nLowPowerState;
/** 
保留字节
*/
    public			byte[]         szReserved = new byte[244];

public NET_SENSOR_STATE(){
}
}

