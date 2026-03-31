package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 从片版本信息
*/
public class NET_PERIPHERAL_CHIP_VERSION extends NetSDKLib.SdkStructure
{
    /**
     * 从片硬件版本信息
    */
    public byte[]           szHardwareVersion = new byte[16];
    /**
     * 从片软件版本信息
    */
    public byte[]           szSoftwareVersion = new byte[64];
    /**
     * 是否匹配, -1:未知, 0:匹配, 1:不匹配
    */
    public int              nIsMatching;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[252];

    public NET_PERIPHERAL_CHIP_VERSION() {
    }
}

