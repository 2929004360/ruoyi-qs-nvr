package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * EAS设备能力集支持的报警信息
*/
public class NET_EAS_DEVICE_ALARM_INFO_CAPS extends NetSDKLib.SdkStructure
{
    /**
     * 支持的报警信息个数
    */
    public int              nAlarmInfoCapsCount;
    /**
     * 支持的报警信息: All:所有的报警信息, FalseAlarm:误报信息, AlarmCount:报警计数, AlarmSignalAtrength:报警信号强度,参见枚举定义 EM_EAS_DEVICE_ALARM_INFO_CAPS
    */
    public int[]            emAlarmInfoCaps = new int[32];
    /**
     * 保留字节
    */
    public byte[]           szResvered = new byte[60];

    public NET_EAS_DEVICE_ALARM_INFO_CAPS() {
    }
}

