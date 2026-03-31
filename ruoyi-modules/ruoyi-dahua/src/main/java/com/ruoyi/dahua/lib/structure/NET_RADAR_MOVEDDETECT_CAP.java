package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 雷达场景变更能力
 * @date 2022/08/04 10:13:31
 */
public class NET_RADAR_MOVEDDETECT_CAP extends NetSDKLib.SdkStructure {
    /**
     * 是否支持该能力
     */
    public int              bSupport;
    /**
     * 预留
     */
    public byte[]           byReserved = new byte[252];

    public NET_RADAR_MOVEDDETECT_CAP() {
    }
}

