package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 广告分屏模式设备广告显示配置
*/
public class NET_ADVERTISE_DISPLAY extends NetSDKLib.SdkStructure
{
    /**
     * 广告分屏模式情况下视频广告如何显示 0：原比例播放，1:全屏播放模式
    */
    public int              nAdvertiseVideoDisplay;
    /**
     * 广告分屏模式情况下图片广告如何显示 0：原比例播放，1:全屏播放模式
    */
    public int              nAdvertisePicDisplay;
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[128];

    public NET_ADVERTISE_DISPLAY() {
    }
}

