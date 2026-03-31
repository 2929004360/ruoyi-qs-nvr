package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  考试计划 
* @date 2022/05/16 20:41:47
*/
public class NET_EXAM_RECORDING_PLAN_INFO extends NetSDKLib.SdkStructure {
/** 
考生姓名，采用UTF8编码
*/
    public			byte[]         szName = new byte[64];
/** 
考号
*/
    public			byte[]         szNumber = new byte[64];
/** 
摄像头IP个数
*/
    public			int            nCameraIPNum;
/** 
摄像头IP数组
*/
    public			byte[]         szCameraIP = new byte[8*32];
/** 
开始时间
*/
    public NetSDKLib.NET_TIME stuStartTime = new NetSDKLib.NET_TIME();
/** 
结束时间
*/
    public NetSDKLib.NET_TIME stuEndTime = new NetSDKLib.NET_TIME();
/** 
保留字节
*/
    public			byte[]         szResvered = new byte[256];

public NET_EXAM_RECORDING_PLAN_INFO(){
}
}

