package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 图片中校准点的像素坐标
 * @date 2022/08/04 10:13:32
 */
public class NET_RADAR_PIXELPOINT extends NetSDKLib.SdkStructure {
    /**
     * X像素坐标区间[0,8191]
     */
    public int              nPixelPointX;
    /**
     * Y像素坐标区间[0,8191]
     */
    public int              nPixelPointY;

    public NET_RADAR_PIXELPOINT() {
    }
}

