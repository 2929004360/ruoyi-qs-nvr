package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 温度信息
*/
public class NET_QUERY_TEMPER_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 平均温度
    */
    public float            fTemperAve;
    /**
     * 最高的温度
    */
    public float            fTemperMax;
    /**
     * 最低的温度
    */
    public float            fTemperMin;
    /**
     * 保留字节
    */
    public byte[]           byReserved = new byte[128];

    public NET_QUERY_TEMPER_INFO() {
    }
}

