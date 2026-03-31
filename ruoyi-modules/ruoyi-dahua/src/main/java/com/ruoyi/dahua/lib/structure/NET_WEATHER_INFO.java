package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 气象信息结构
*/
public class NET_WEATHER_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 风速,单位m/s,	范围[0.0,60.0]
    */
    public float            fWindSpeed;
    /**
     * 温度,单位摄氏度，范围[-40.0,+80.0]
    */
    public float            fTemperature;
    /**
     * 后缀%,范围[0.0,100.0]
    */
    public float            fHumidity;
    /**
     * 光照,单位W/(m*m),范围[0,2000]
    */
    public int              nLight;
    /**
     * 风向,单位度,范围[0,360)
    */
    public float            fWindDirection;
    /**
     * 气压,单位百帕,范围[10~1100]
    */
    public float            fAirPressure;
    /**
     * 累计雨量,单位0.01mm
    */
    public int              nRainfall;
    /**
     * 保留字节
    */
    public byte[]           byReserved = new byte[2044];

    public NET_WEATHER_INFO() {
    }
}

