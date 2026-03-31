package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description  RF干扰报警上报事件 (对应 NET_ALARM_RF_JAMMING)
 * @date 2022/7/19 10:45
 */
public class NET_ALARM_RF_JAMMING_INFO extends NetSDKLib.SdkStructure {
    public   int            nAction;                              // 事件动作 0:脉冲
    public 	int             nChannel;                             // 通道号
    public 	NET_TIME_EX     stuUTC = new NET_TIME_EX();           // 事件发生的时间,标准的（不带时区偏差的）UTC时间
    public 	byte[]          szDeviceName = new byte[256];         // 设备名称
    public 	byte[]          szReserved = new byte[1024];          // 保留字节
}

