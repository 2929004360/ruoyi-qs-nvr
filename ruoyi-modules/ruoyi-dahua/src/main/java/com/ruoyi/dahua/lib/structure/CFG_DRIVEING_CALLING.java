package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 打电话报警配置
 * @date 2023/03/31 11:19:01
 */
public class CFG_DRIVEING_CALLING extends NetSDKLib.SdkStructure {
    /**
     * 使能
     */
    public int              bEnable;
    /**
     * 语音播报使能
     */
    public int              bVoiceBroadcastEnable;
    /**
     * 低头持续时间 取值范围：0-10，默认值为2
     */
    public int              nBowDownTime;
    /**
     * 打电话持续时间, 取值范围：0-10，默认值为2
     */
    public int              nCallingTime;
    /**
     * 时间间隔, 取值范围：0-65536，默认值为60
     */
    public int              nAlarmInterval;
    /**
     * 报警触发速度, 取值范围：0-200，默认值为30
     */
    public int              nAlarmSpeed;
    /**
     * 报警上报速度, 取值范围：0-200，默认值为30
     */
    public int              nAlarmReportSpeed;
    /**
     * 保留
     */
    public byte[]           byReserved = new byte[1024];

    public CFG_DRIVEING_CALLING() {
    }
}

