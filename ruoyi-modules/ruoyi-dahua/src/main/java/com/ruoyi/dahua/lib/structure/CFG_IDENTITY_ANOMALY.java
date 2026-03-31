package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 身份异常报警配置 IdentityAnomalyCfg
 * @date 2023/03/31 11:19:02
 */
public class CFG_IDENTITY_ANOMALY extends NetSDKLib.SdkStructure {
    /**
     * 使能
     */
    public int              bEnable;
    /**
     * 语音播报使能
     */
    public int              bVoiceBroadcastEnable;
    /**
     * 时间间隔, 取值范围：0-65536，默认值为60
     */
    public int              nAlarmInterval;
    /**
     * 报警触发速度, 取值范围：0-200，默认值为30
     */
    public int              nAlarmSpeed;
    /**
     * 保留
     */
    public byte[]           byReserved = new byte[1016];

    public CFG_IDENTITY_ANOMALY() {
    }
}

