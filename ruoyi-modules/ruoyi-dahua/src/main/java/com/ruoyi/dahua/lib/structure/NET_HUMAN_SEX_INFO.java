package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  性别过滤条件 
* @origin autoTool
* @date 2023/05/29 11:40:41
*/
public class NET_HUMAN_SEX_INFO extends NetSDKLib.SdkStructure {
/** 
是否过滤启用: TRUE-下发该配置  FALSE-不下发该配置
*/
    public			int            bEnable;
/** 
过滤显示的性别的个数,最大值是2
*/
    public			int            nShowListNums;
/** 
过滤显示的性别
*/
    public			int[]          emHumanSexShowList = new int[2];
/** 
保留字节
*/
    public			byte[]         szReserved = new byte[256];

public			NET_HUMAN_SEX_INFO(){
}
}

