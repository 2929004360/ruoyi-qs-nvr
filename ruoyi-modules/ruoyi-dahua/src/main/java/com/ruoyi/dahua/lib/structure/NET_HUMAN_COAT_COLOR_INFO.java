package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  上衣颜色过滤条件 
* @origin autoTool
* @date 2023/05/29 11:40:42
*/
public class NET_HUMAN_COAT_COLOR_INFO extends NetSDKLib.SdkStructure {
/** 
是否过滤启用: TRUE-下发该配置  FALSE-不下发该配置
*/
    public			int            bEnable;
/** 
过滤出对应的上衣颜色的个数,最大值是32
*/
    public			int            nShowListNums;
/** 
上衣颜色
*/
    public			int[]          emHumanCoatColorShowList = new int[32];
/** 
保留字节
*/
    public			byte[]         szReserved = new byte[256];

public			NET_HUMAN_COAT_COLOR_INFO(){
}
}

