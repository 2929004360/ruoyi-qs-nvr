package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  智能事件数据信息 
* @date 2022/11/02 13:53:03
*/
public class NET_IVSEVENT_EVENT_INFO extends NetSDKLib.SdkStructure {
/** 
通道号 从0开始,-1表示通道号未知
*/
    public			int            nChannel;
/** 
事件编号
*/
    public			int            nId;
/** 
事件类型,值含义参考 EVENT_IVS_ALL 下面的智能事件类型宏定义
*/
    public			int            dwEventCode;
/** 
报警诊断类型
0:未知
1:未处理
2:正确
3:错误
4:重复正确报警
5:重复错误报警
*/
    public			int            nVaild;
/**
 事件发生的时间
*/
    public NET_TIME_EX      stuTime = new NET_TIME_EX();
/** 
保留字节
*/
    public			byte[]         byReserved = new byte[1020];

public NET_IVSEVENT_EVENT_INFO(){
}
}

