package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  分组事件联动 
* @date 2022/12/01 16:27:10
*/
public class NET_XRAY_OBJECT_GROUP_EVENT_HANDLER extends NetSDKLib.SdkStructure {
/** 
报警输出通道号列表 一维数组，每个成员表示对应的通道需要执行报警输出，通道号从0开始。
*/
    public			int[]          nAlarmOutChannels = new int[64];
/** 
报警输出通道号个数
*/
    public			int            nAlarmOutChannelsNum;
/** 
报警输出使能
*/
    public			int            bAlarmOutEnable;
/** 
报警输入停止后，输出延时时间（秒） 范围[1,300]
*/
    public			int            nAlarmOutLatch;
/** 
是否上传到报警中心服务器   报警中心不需要登陆设备，由设备直接推送给报警中心的业务
*/
    public			int            bMessageEnable;
/** 
语音提示
*/
    public			int            bVoiceEnable;
/** 
语音提示联动
*/
    public			NET_EVENT_HANDLER_VOICE stuVoice = new NET_EVENT_HANDLER_VOICE();
/** 
传送带是否停止(X光机需求）
*/
    public			int            bMoveStop;
/** 
蜂鸣
*/
    public			int            bBeepEnable;
/** 
预留
*/
    public			byte[]         szReserved = new byte[256];

public			NET_XRAY_OBJECT_GROUP_EVENT_HANDLER(){
}
}

