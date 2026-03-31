package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description 灯光支持的模式信息
 * @origin autoTool
 * @date 2023/10/19 17:27:51
 */
public class NET_MODES_COMPLEX_LIGHT extends NetSDKLib.SdkStructure {
    /**
     * /红外灯支持的模式
     */
    public int[]            anInfraredLight = new int[3];
    /**
     * /红外灯支持的模式数量
     */
    public int              nInfraredLightLen;
    /**
     * /白光灯支持的模式
     */
    public int[]            anWhiteLight = new int[3];
    /**
     * /白光灯支持的模式数量
     */
    public int              nWhiteLightLen;
    /**
     * /激光灯支持的模式
     */
    public int[]            anLaserLight = new int[3];
    /**
     * /激光灯支持的模式数量
     */
    public int              nLaserLightLen;
    /**
     * /智能混光灯支持的模式
     */
    public int[]            emAIMixLight = new int[8];
    /**
     * /智能混光灯支持的模式数量
     */
    public int              nAIMixLight;
    /**
     * /保留字节
     */
    public byte[]           byReserved = new byte[92];

    public NET_MODES_COMPLEX_LIGHT() {
    }
}

