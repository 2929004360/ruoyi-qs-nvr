 package com.ruoyi.dahua.lib.structure;


 import com.ruoyi.dahua.lib.NetSDKLib;

 /**
 * @author ： 260611
 * @description ： 车辆信息
 * @since ： Created in 2022/01/18 14:34
 */

public class DREGS_UNCOVERED_VEHICLE_INFO extends NetSDKLib.SdkStructure {
     /** 
      * 车牌号码
      */
    public			byte[]         szPlateNumber = new byte[64];
     /** 
      * 包围盒
      */
    public			NET_RECT       stuBoundingBox = new NET_RECT();
     /** 
      * 预留字节
      */
    public			byte[]         byReserved = new byte[512];
}

