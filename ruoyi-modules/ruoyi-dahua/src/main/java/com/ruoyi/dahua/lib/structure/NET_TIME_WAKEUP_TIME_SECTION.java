package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 按时间段休眠信息
*/
public class NET_TIME_WAKEUP_TIME_SECTION extends NetSDKLib.SdkStructure
{
    /**
     * 每周7天，每天6个时间段,参见结构体定义 {@link com.ruoyi.dahua.lib.structure.NET_TSECT}
    */
    public NET_TSECT_ARRAY_6[] stuAwakeSection = new NET_TSECT_ARRAY_6[7];
    /**
     * 处于按时间段休眠方式下时，可以支持唤醒时间段内的定时休眠。此字段表示定时休眠的时间间隔。单位为分钟。仅当Mode为" TimeSection "时生效
    */
    public int              nWakeUpInterval;
    /**
     * 保留字节
    */
    public byte[]           szResvered = new byte[1020];

    public NET_TIME_WAKEUP_TIME_SECTION() {
        for(int i = 0; i < stuAwakeSection.length; i++){
            stuAwakeSection[i] = new NET_TSECT_ARRAY_6();
        }
    }
}

