package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 传感器类型报警的数值与emSenseMethod关联
*/
public class NET_SENSE_VALUE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 数值
    */
    public double           dValue;
    /**
     * 单位
    */
    public byte[]           szUnit = new byte[32];
    /**
     * 预留
    */
    public byte[]           szReserved = new byte[128];

    public NET_SENSE_VALUE_INFO() {
    }
}

