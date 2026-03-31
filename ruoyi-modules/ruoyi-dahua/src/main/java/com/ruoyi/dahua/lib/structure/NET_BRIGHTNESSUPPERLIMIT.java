package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 亮度上限调节
*/
public class NET_BRIGHTNESSUPPERLIMIT extends NetSDKLib.SdkStructure
{
    /**
     * 是否支持亮度上限调节
    */
    public int              bSupport;
    /**
     * 哪些灯光支持亮度上限调节,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_LC_LIGHT_TYPE}
    */
    public int[]            emLightType = new int[10];
    /**
     * 白光灯支持的模式,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_LC_BRIGHTNESSUPPERLIMIT_MODE}
    */
    public int[]            emWhiteLight = new int[10];
    /**
     * 红外灯支持的模式,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_LC_BRIGHTNESSUPPERLIMIT_MODE}
    */
    public int[]            emInfraredLight = new int[10];
    /**
     * 激光灯支持的模式,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_LC_BRIGHTNESSUPPERLIMIT_MODE}
    */
    public int[]            emLaserLight = new int[10];
    /**
     * 保留字节
    */
    public byte[]           byReserved = new byte[124];

    public NET_BRIGHTNESSUPPERLIMIT() {
    }
}

