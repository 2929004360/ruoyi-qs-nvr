package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  胡子过滤条件 
* @origin autoTool
* @date 2023/05/29 11:40:42
*/
public class NET_HUMAN_BEARD_INFO extends NetSDKLib.SdkStructure {
/** 
是否过滤启用: TRUE-下发该配置  FALSE-不下发该配置
*/
    public			int            bEnable;
/** 
过滤显示的胡子情况的个数,最大值是2
*/
    public			int            nShowListNums;
/** 
胡子情况
*/
    public			int[]          emHumanBeardShowList = new int[2];
/** 
保留字节
*/
    public			byte[]         szReserved = new byte[256];

public			NET_HUMAN_BEARD_INFO(){
}
}

