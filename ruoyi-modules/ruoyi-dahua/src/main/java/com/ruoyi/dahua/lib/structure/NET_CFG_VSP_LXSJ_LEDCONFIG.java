 package com.ruoyi.dahua.lib.structure;
 import com.ruoyi.dahua.lib.NetSDKLib;

 /**LED屏幕配置  */
public class NET_CFG_VSP_LXSJ_LEDCONFIG extends NetSDKLib.SdkStructure {
/** 车位个数*/
    public			int            nParkingNum;
/** 车位配置*/
    public			NET_CFG_VSP_LXSJ_PARKING[] stuParking = (NET_CFG_VSP_LXSJ_PARKING[])new NET_CFG_VSP_LXSJ_PARKING().toArray(256);
}

