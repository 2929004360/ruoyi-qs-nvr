package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 云台水平旋转组限制信息
*/
public class NET_CFG_HORIZONTAL_ROTATION_LIMIT_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 限制使能
    */
    public int              bLimitEnable;
    /**
     * 限制模式 0:左右 1：上下
    */
    public int              nLimitMode;
    /**
     * 保留字节
    */
    public byte[]           byReserved = new byte[256];

    public NET_CFG_HORIZONTAL_ROTATION_LIMIT_INFO() {
    }
}

