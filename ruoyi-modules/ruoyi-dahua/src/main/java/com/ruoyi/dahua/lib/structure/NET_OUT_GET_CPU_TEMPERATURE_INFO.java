package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetCPUTemperature 接口输出参数
*/
public class NET_OUT_GET_CPU_TEMPERATURE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * CPU温度,单位：摄氏度，取值范围,10.0~90.0
    */
    public float            fTemperature;

    public NET_OUT_GET_CPU_TEMPERATURE_INFO() {
        this.dwSize = this.size();
    }
}

