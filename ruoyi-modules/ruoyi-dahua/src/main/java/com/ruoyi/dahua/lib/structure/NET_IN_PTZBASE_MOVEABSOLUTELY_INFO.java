package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @description:
 * @author: 251589
 * @time: 2020/11/17 19:41
 */
public class NET_IN_PTZBASE_MOVEABSOLUTELY_INFO extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 1 表示显示倍率; 2 保留，内部用; 3 表示映射倍率值; 如为 0 则默认映射倍率值
     */
    public int              nZoomFlag;
    /**
     * 云台绝对移动位置云台绝对定位参数,扩大10倍
     * 云台水平坐标(0~3600)
     * 云台垂直坐标(-1800~1800)
     * 倍率值，范围：nZoomFlag为1时(0~最大显示倍率*10)，nZoomFlag为3时(0~16384)
     */
    public NetSDKLib.NET_PTZSPACE_UNNORMALIZED stuPosition = new NetSDKLib.NET_PTZSPACE_UNNORMALIZED();
    /**
     * 若无speed则表示默认速度运动 P，T，以0.01度/秒为单位，扩大100倍显示，
     * 范围与PtzSpeedLevel中的范围保持一致[0，100000]，
     * 水平和垂直分别最大不会超过PtzSpeedLevel中最大档位水平和垂直的最大值，zoom变倍速度为0~100。
     */
    public NetSDKLib.NET_PTZSPACE_UNNORMALIZED stuSpeed = new NetSDKLib.NET_PTZSPACE_UNNORMALIZED();
    /**
     * 编码
     */
    public int              nRegionCode;
    /**
     * 字节对齐
     */
    public byte[]           byReserved = new byte[444];

    public NET_IN_PTZBASE_MOVEABSOLUTELY_INFO() {
        this.dwSize = this.size();
    }
}

