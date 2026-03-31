package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 线扫信息
*/
public class NET_AUTOSCAN_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 线扫号名称
    */
    public byte[]           szName = new byte[32];
    /**
     * 该线扫左边界是否生效
    */
    public int              bLeftEnable;
    /**
     * 该线扫右边界是否生效
    */
    public int              bRightEnable;
    /**
     * 线扫速度,0~7
    */
    public int              nScanSpeed;
    /**
     * 预留
    */
    public byte[]           byReserved = new byte[948];

    public NET_AUTOSCAN_INFO() {
    }
}

