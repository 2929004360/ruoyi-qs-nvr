package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 低功耗设备定时唤醒功能信息
*/
public class NET_SLEEPING_CONTROL_TIME_WAKEUP extends NetSDKLib.SdkStructure
{
    /**
     * 定时唤醒使能
    */
    public int              bEnable;
    /**
     * 定时唤醒的方式,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_TIME_WAKEUP_MODE}
    */
    public int              emMode;
    /**
     * 按时间段休眠信息, 仅当Mode为TimeSection时生效,参见结构体定义 {@link NET_TIME_WAKEUP_TIME_SECTION}
    */
    public NET_TIME_WAKEUP_TIME_SECTION stuTimeSection = new NET_TIME_WAKEUP_TIME_SECTION();
    /**
     * 保留字节
    */
    public byte[]           szResvered = new byte[128];

    public NET_SLEEPING_CONTROL_TIME_WAKEUP() {
    }
}

