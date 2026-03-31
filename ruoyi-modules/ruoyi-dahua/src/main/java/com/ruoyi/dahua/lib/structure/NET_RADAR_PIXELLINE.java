package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 图片中线段的像素坐标
 * @date 2022/08/04 10:13:32
 */
public class NET_RADAR_PIXELLINE extends NetSDKLib.SdkStructure {
    /**
     * 左顶点的X坐标
     */
    public int              nLeftX;
    /**
     * 左顶点的Y坐标
     */
    public int              nLeftY;
    /**
     * 右顶点的X坐标
     */
    public int              nRightX;
    /**
     * 右顶点的Y坐标
     */
    public int              nRightY;
    /**
     * 保留字节
     */
    public byte[]           byReserved = new byte[64];

    public NET_RADAR_PIXELLINE() {
    }
}

