package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 本地参数配置
*/
public class NET_LOCAL_PARAMETERS_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 物体距离(小数部分)
    */
    public float            fDistanceDecimalPart;
    /**
     * 是否启用本地配置
    */
    public int              bEnable;
    /**
     * 目标距离
    */
    public int              nObjectDistance;
    /**
     * 目标辐射系数
    */
    public float            fObjectEmissivity;
    /**
     * 目标反射温度
    */
    public float            fReflectedTemp;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[508];

    public NET_LOCAL_PARAMETERS_INFO() {
    }
}

