package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 智能动态检测对象
*/
public class NET_SMART_MOTION_DETECT_OBJECT extends NetSDKLib.SdkStructure
{
    /**
     * 动态检测人使能
    */
    public int              bHuman;
    /**
     * 动态检测车使能
    */
    public int              bVehicle;
    /**
     * 保留字节
    */
    public byte[]           byReserved = new byte[1020];

    public NET_SMART_MOTION_DETECT_OBJECT() {
    }
}

