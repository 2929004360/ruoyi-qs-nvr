package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  车标过滤配置 
* @origin autoTool
* @date 2023/05/29 11:40:45
*/
public class NET_VEHICLE_BRAND_INFO extends NetSDKLib.SdkStructure {
/** 
是否过滤启用: TRUE-下发该配置  FALSE-不下发该配置
*/
    public			int            bEnable;
/** 
车标的个数,最大值是256
*/
    public			int            nShowListNums;
/** 
车标对应的枚举值, 数字到具体品牌映射见映射表(由算法统一提供), 目前有效值为0-562
*/
    public			int[]          nBrandShowList = new int[256];
/** 
保留字节
*/
    public			byte[]         szReserved = new byte[256];

public			NET_VEHICLE_BRAND_INFO(){
}
}

