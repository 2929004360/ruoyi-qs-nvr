package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  丢帧检测 
* @date 2022/09/15 15:41:32
*/
public class CFG_VIDEO_LOSS_FRAME_DETECTION extends NetSDKLib.SdkStructure {
/** 
使能配置
*/
    public			int            bEnable;
/** 
预警阈值, 范围:1~100
*/
    public			int            nWarnThreshold;
/** 
报警阈值, 范围:1~100
*/
    public			int            nAlarmThreshold;
/** 
最短持续时间, 单位: 秒, 取值: 0~65535
*/
    public			int            nMinDuration;
/** 
保留字节
*/
    public			byte[]         szReserver = new byte[256];

public CFG_VIDEO_LOSS_FRAME_DETECTION(){
}
}

