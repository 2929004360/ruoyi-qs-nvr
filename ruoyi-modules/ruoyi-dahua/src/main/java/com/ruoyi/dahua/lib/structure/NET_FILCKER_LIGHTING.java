package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description 灯光闪烁相关信息
 * @origin autoTool
 * @date 2023/10/19 17:27:53
 */
public class NET_FILCKER_LIGHTING extends NetSDKLib.SdkStructure {
    /**
     * /是否支持灯光闪烁
     */
    public int              bSupported;
    /**
     * /支持灯光联动能力集
     */
    public NET_LINKING_ABILITY stuAbility = new NET_LINKING_ABILITY();
    /**
     * /闪烁灯光类型
     */
    public int[]            anLightType = new int[3];
    /**
     * /闪烁灯光数量
     */
    public int              nLightTypeLen;
    /**
     * /闪烁间隔时间范围
     */
    public int[]            anFilckerIntevalTime = new int[2];
    /**
     * /闪烁可配置次数
     */
    public int[]            anFilckerTimes = new int[2];
    /**
     * /保留字节
     */
    public byte[]           byReserved = new byte[128];

    public NET_FILCKER_LIGHTING() {
    }
}

