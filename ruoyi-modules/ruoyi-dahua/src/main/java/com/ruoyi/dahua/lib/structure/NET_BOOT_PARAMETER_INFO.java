package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 具体参数信息
*/
public class NET_BOOT_PARAMETER_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 具体参数名称
    */
    public byte[]           szName = new byte[32];
    /**
     * 具体参数信息
    */
    public byte[]           szParameterValue = new byte[256];
    /**
     * 保留字节
    */
    public byte[]           bReserved = new byte[256];

    public NET_BOOT_PARAMETER_INFO() {
    }
}

