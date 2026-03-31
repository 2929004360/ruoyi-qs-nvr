package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description 灯光常亮信息
 * @origin autoTool
 * @date 2023/10/19 17:27:54
 */
public class NET_KEEP_LIGHTING extends NetSDKLib.SdkStructure {
    /**
     * /是否支持灯光常亮
     */
    public int              bSupported;
    /**
     * /支持灯光联动能力集
     */
    public NET_LINKING_ABILITY stuAbility = new NET_LINKING_ABILITY();
    /**
     * /常亮灯光类型
     */
    public int[]            anLightType = new int[3];
    /**
     * /常亮灯光数量
     */
    public int              nLightTypeLen;
    /**
     * / 保留字节
     */
    public byte[]           byReserved = new byte[128];

    public NET_KEEP_LIGHTING() {
    }
}

