package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  车上挂件过滤条件 
* @origin autoTool
* @date 2023/05/29 11:40:46
*/
public class NET_VEHICLE_ATTACHMENT_INFO extends NetSDKLib.SdkStructure {
/** 
是否过滤启用: TRUE-下发该配置  FALSE-不下发该配置
*/
    public			int            bEnable;
/** 
车内饰品情况的个数,最大值是16
*/
    public			int            nShowListNums;
/** 
车内饰品情况
*/
    public			int[]          emVehicleAttachmentShowList = new int[16];
/** 
保留字节
*/
    public			byte[]         szReserved = new byte[256];

public			NET_VEHICLE_ATTACHMENT_INFO(){
}
}

