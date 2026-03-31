package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 防补光过曝
*/
public class NET_ANTIFILLLIGHTOVEREXPOSURE extends NetSDKLib.SdkStructure
{
    /**
     * 支持防补光过曝的目标类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_LC_TARGETMODE}
    */
    public int[]            emTargetMode = new int[16];
    /**
     * 防补光过曝的目标类型数量
    */
    public int              nTargetModeNumber;
    /**
     * 防补光配置是否不与强光抑制、背光补偿互斥
    */
    public int              bNotConflitBacklight;
    /**
     * 是否支持智能补光车牌优先联动选中目标车
    */
    public int              bSupportedPlateLinktoVehicle;
    /**
     * 是否支持智能补光人脸优先联动选中目标人
    */
    public int              bSupportedFaceLinktoHuman;
    /**
     * 保留字节
    */
    public byte[]           byReserved = new byte[128];

    public NET_ANTIFILLLIGHTOVEREXPOSURE() {
    }
}

