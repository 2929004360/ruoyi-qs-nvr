package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 计算图片对应的地图实际长度入参(对应 EM_RADAR_OPERATE_TYPE_CALCULATEREALSIZE)
 * @date 2022/08/04 10:13:32
 */
public class NET_IN_RADAR_CALCULATEREALSIZE extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 通道号
     */
    public int              nChannel;
    /**
     * 图片中线段的像素坐标
     */
    public NET_RADAR_PIXELLINE stuPixelLine = new NET_RADAR_PIXELLINE();
    /**
     * 图片中线段代表的实际长度，单位米
     */
    public double           dDistance;

    public NET_IN_RADAR_CALCULATEREALSIZE() {
        this.dwSize = this.size();
    }
}

