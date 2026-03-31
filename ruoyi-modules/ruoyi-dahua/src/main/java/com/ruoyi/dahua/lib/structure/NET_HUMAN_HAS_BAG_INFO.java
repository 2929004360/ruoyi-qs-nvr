package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  背包过滤条件 
* @origin autoTool
* @date 2023/05/29 11:40:43
*/
public class NET_HUMAN_HAS_BAG_INFO extends NetSDKLib.SdkStructure {
/** 
是否过滤启用: TRUE-下发该配置  FALSE-不下发该配置
*/
    public			int            bEnable;
/** 
需要过滤的背包情况的个数,最大值是16
*/
    public			int            nShowListNums;
/** 
背包情况
*/
    public			int[]          emHumanHasBagShowList = new int[16];
/** 
保留字节
*/
    public			byte[]         szReserved = new byte[256];

public			NET_HUMAN_HAS_BAG_INFO(){
}
}

