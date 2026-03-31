package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  人体拍摄角度 
* @origin autoTool
* @date 2023/05/29 11:40:44
*/
public class NET_HUMAN_ANGLE_INFO extends NetSDKLib.SdkStructure {
/** 
是否过滤启用: TRUE-下发该配置  FALSE-不下发该配置
*/
    public			int            bEnable;
/** 
需要过滤的拍摄角度类型的个数,最大值是16
*/
    public			int            nShowListNums;
/** 
需要过滤的拍摄角度类型
*/
    public			int[]          emHumanAngleShowList = new int[16];
/** 
保留字节
*/
    public			byte[]         szReserved = new byte[256];

public			NET_HUMAN_ANGLE_INFO(){
}
}

