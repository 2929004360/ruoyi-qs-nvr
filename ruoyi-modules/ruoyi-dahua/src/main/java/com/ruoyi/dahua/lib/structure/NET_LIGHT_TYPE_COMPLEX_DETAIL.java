package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description 灯光组信息
 * @origin autoTool
 * @date 2023/10/19 17:27:51
 */
public class NET_LIGHT_TYPE_COMPLEX_DETAIL extends NetSDKLib.SdkStructure {
    /**
     * /近光灯组灯类型
     */
    public int[]            anNearLight = new int[4];
    /**
     * /近光灯组灯数量
     */
    public int              nNearLightLen;
    /**
     * /中光灯组灯类型
     */
    public int[]            anMiddleLight = new int[4];
    /**
     * /中光灯组灯数量
     */
    public int              nMiddleLightLen;
    /**
     * /远光灯组灯类型
     */
    public int[]            anFarLight = new int[4];
    /**
     * /远光灯组灯数量
     */
    public int              nFarLightLen;
    /**
     * / 保留字节
     */
    public byte[]           byReserved = new byte[128];

    public NET_LIGHT_TYPE_COMPLEX_DETAIL() {
    }
}

