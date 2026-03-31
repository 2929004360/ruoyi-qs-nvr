package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 属性报警信息
*/
public class NET_FEATURE_ALARM_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 报警模式
    */
    public byte[]           szAlarmMode = new byte[64];
    /**
     * 预留字段
    */
    public byte[]           szReserved = new byte[256];

    public NET_FEATURE_ALARM_INFO() {
    }
}

