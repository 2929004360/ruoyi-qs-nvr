package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * 轮巡控制类型
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 19:35
 */
public class PTZ_SPACE_UNIT extends NetSDKLib.SdkStructure {
    /**
     * 云台水平运动位置,有效范围：[0,3600]
     */
    public int              nPositionX;
    /**
     * 云台垂直运动位置,有效范围：[-1800,1800]
     */
    public int              nPositionY;
    /**
     * 云台光圈变动位置,有效范围：[0,128]
     */
    public int              nZoom;
    /**
     * 预留32字节
     */
    public byte             szReserve[] = new byte[32];
}

