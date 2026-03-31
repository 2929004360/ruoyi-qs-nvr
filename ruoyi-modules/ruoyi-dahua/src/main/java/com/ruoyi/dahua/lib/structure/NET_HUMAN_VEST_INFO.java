package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  安全背心合规性检测信息 
* @origin autoTool
* @date 2023/05/29 11:40:44
*/
public class NET_HUMAN_VEST_INFO extends NetSDKLib.SdkStructure {
/** 
是否过滤启用: TRUE-下发该配置  FALSE-不下发该配置
*/
    public			int            bEnable;
/** 
是否有穿反光背心的类型的个数,最大值是2
*/
    public			int            nShowListNums;
/** 
是否有穿反光背心的类型
*/
    public			int[]          emHumanVestShowList = new int[2];
/** 
保留字节
*/
    public			byte[]         szReserved = new byte[256];

public			NET_HUMAN_VEST_INFO(){
}
}

