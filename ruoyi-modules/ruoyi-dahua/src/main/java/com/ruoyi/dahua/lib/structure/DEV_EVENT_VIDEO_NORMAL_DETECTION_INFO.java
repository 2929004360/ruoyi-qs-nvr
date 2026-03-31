package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description 视频正常事件,在视频诊断检测周期结束时,将未报错的诊断项上报正常事件
* @date 2022/03/29 13:59:54
*/
public class DEV_EVENT_VIDEO_NORMAL_DETECTION_INFO extends NetSDKLib.SdkStructure {
/** 
通道号
*/
    public			int            nChannelID;
/** 
0:脉冲
*/
    public			int            nAction;
/** 
事件编号，用来唯一标志一个事件
*/
    public			int            nEventID;
/** 
事件发生的时间,单位秒
*/
    public NET_TIME_EX      UTC = new NET_TIME_EX();
/** 
时间戳(单位是毫秒)
*/
    public			double         dbPTS;
/** 
视频检测类型 {@link com.ruoyi.dahua.lib.enumeration.EM_VIDEO_ABNORMAL_DETECT_TYPE}
*/
    public			int            emDetectType;
/** 
保留字节
*/
    public			byte[]         bReserved = new byte[260];
}

