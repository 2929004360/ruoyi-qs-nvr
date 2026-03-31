package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/** 
* @author 291189
* @description  年龄过滤条件 
* @origin autoTool
* @date 2023/05/29 11:40:41
*/
public class NET_HUMAN_AGE_INFO extends NetSDKLib.SdkStructure {
/** 
是否过滤启用: TRUE-下发该配置  FALSE-不下发该配置
*/
    public			int            bEnable;
/** 
过滤显示的年龄段的个数,最大值是16
*/
    public			int            nShowListNums;
/** 
过滤显示的年龄段
*/
    public			int[]          emHumanAgeShowList = new int[16];
/** 
年龄段信息的描述信息, 内存由用户申请和释放, 申请大小sizeof(NET_HUMAN_DESC_INFO)*nShowListNums
*/
    public Pointer          pstuHumanDesc;
/** 
保留字节
*/
    public			byte[]         szReserved = new byte[256];

public			NET_HUMAN_AGE_INFO(){
}
}

