package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 风扇能力
*/
public class CFG_PTZ_FAN_CAPS extends NetSDKLib.SdkStructure
{
    /**
     * 是否支持风扇控制
    */
    public int              bSupport;
    /**
     * 是否支持除雾
    */
    public int              bClearFogSupport;
    /**
     * 是否支持风扇电源管理
    */
    public int              bFanPowerSupport;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1020];

    public CFG_PTZ_FAN_CAPS() {
    }
}

