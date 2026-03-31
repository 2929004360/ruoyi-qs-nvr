package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 各个视角电流值
*/
public class NET_XRAY_SOURCE_CURRENT_ARRAY extends NetSDKLib.SdkStructure
{
    /**
     * 视角A电流值
    */
    public int              nViewA;
    /**
     * 视角B电流值
    */
    public int              nViewB;
    /**
     * 视角C电流值
    */
    public int              nViewC;
    /**
     * 视角D电流值
    */
    public int              nViewD;
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[32];

    public NET_XRAY_SOURCE_CURRENT_ARRAY() {
    }
}

