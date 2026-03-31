package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 换人驾驶报警配置 SubstitutionDrivingCfg
 * @date 2023/03/31 11:19:02
 */
public class CFG_SUBSTITUTION_DRIVING extends NetSDKLib.SdkStructure {
    /**
     * 使能
     */
    public int              bEnable;
    /**
     * 语音播报使能
     */
    public int              bVoiceBroadcastEnable;
    /**
     * 保留
     */
    public byte[]           byReserved = new byte[1024];

    public CFG_SUBSTITUTION_DRIVING() {
    }
}

