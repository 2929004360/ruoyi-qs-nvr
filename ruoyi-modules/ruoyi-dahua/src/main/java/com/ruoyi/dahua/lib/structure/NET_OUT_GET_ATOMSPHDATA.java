package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetAtomsphData接口出参
*/
public class NET_OUT_GET_ATOMSPHDATA extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 风速，单位m/s
    */
    public float            fWindSpeed;
    /**
     * 风向，单位度
    */
    public float            fWindDirection;
    /**
     * 湿度，单位%
    */
    public float            fHumidity;
    /**
     * 温度，单位摄氏度
    */
    public float            fTemperture;
    /**
     * 气压，单位百帕
    */
    public float            fAirPressure;
    /**
     * 光照，单位w/m2
    */
    public float            fSunBathe;

    public NET_OUT_GET_ATOMSPHDATA() {
        this.dwSize = this.size();
    }
}

