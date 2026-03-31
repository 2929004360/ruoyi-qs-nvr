package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * APP的能力信息
*/
public class NET_APP_CAPS extends NetSDKLib.SdkStructure
{
    /**
     * 是否支持 web Console
    */
    public int              bShowWebConsole;
    /**
     * 是否支持查看性能
    */
    public int              bShowPerformance;
    /**
     * 是否支持调试
    */
    public int              bShowDebug;
    /**
     * 保留字节
    */
    public byte[]           byReserved = new byte[128];

    public NET_APP_CAPS() {
    }
}

