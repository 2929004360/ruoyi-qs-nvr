package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  安全帽合规性检测信息 
* @origin autoTool
* @date 2023/05/29 11:40:44
*/
public class NET_HUMAN_HELMET_INFO extends NetSDKLib.SdkStructure {
/** 
是否过滤启用: TRUE-下发该配置  FALSE-不下发该配置
*/
    public			int            bEnable;
/** 
是否有戴安全帽，TRUE-有，FALSE-无
*/
    public			int            bHasHelmet;
/** 
安全帽颜色的个数,最大值是32
*/
    public			int            nShowListNums;
/** 
安全帽颜色
*/
    public			int[]          emHumanHelmetColorShowList = new int[32];
/** 
保留字节
*/
    public			byte[]         szReserved = new byte[256];

public			NET_HUMAN_HELMET_INFO(){
}
}

