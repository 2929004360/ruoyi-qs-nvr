package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  对应检测类型(NET_DIAGNOSIS_VIDEO_DELAY)视频延时检测结果 
* @date 2022/08/03 14:23:24
*/
public class NET_VIDEO_DELAY_DETECTIONRESUL extends NetSDKLib.SdkStructure {
/** 
此结构体大小
*/
    public			int            dwSize;
/** 
信令延时,-1表示检测失败, 单位ms
*/
    public			int            nSignalDelay;
/** 
视频流延时,-1表示检测失败, 单位ms
*/
    public			int            nStreamDelay;
/** 
I帧延时,-1表示检测失败 单位ms
*/
    public			int            nIFrameDelay;

public NET_VIDEO_DELAY_DETECTIONRESUL(){
		this.dwSize=this.size();
}
}

