package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetCPUTemperature 接口输入参数
*/
public class NET_IN_GET_CPU_TEMPERATURE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * CPU序号,取值范围0~128
    */
    public int              nIndex;

    public NET_IN_GET_CPU_TEMPERATURE_INFO() {
        this.dwSize = this.size();
    }
}

