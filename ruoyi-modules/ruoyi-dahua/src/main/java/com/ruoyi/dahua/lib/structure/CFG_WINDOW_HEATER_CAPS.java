package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 视窗加热器能力
*/
public class CFG_WINDOW_HEATER_CAPS extends NetSDKLib.SdkStructure
{
    /**
     * 是否支持视窗加热
    */
    public int              bSupport;
    /**
     * 手动开启时间范围，[最小时间，最大时间]，单位分钟
    */
    public int[]            nManualControlPeriod = new int[2];
    /**
     * 手动默认开启时间，不同的设备默认值不同，需要同热设计确认，单位分钟
    */
    public int              nManualDefaultPeriod;
    /**
     * 是否与补光灯功能互斥
    */
    public int              bIsOpposeLight;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1020];

    public CFG_WINDOW_HEATER_CAPS() {
    }
}

