package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 火灾预警云台联动参数使能（ NET_PTZ_LINK 使能）
*/
public class NET_FIREWARN_PTZ_LINK_ENABLE extends NetSDKLib.SdkStructure
{
    /**
     * 联动参数1使能
    */
    public int              bEnable1;
    /**
     * 联动参数2使能
    */
    public int              bEnable2;
    /**
     * 联动参数3使能
    */
    public int              bEnable3;
    /**
     * 所联动云台通道使能
    */
    public int              bChannelEnable;

    public NET_FIREWARN_PTZ_LINK_ENABLE() {
    }
}

