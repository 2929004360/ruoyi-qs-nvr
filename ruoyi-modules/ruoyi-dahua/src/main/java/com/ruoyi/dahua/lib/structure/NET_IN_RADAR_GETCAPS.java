package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 获取雷达能力入参(对应 EM_RADAR_OPERATE_TYPE_GETCAPS)
 * @date 2022/08/04 10:13:31
 */
public class NET_IN_RADAR_GETCAPS extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 通道号
     */
    public int              nChannel;
    /**
     * 雷达ip
     */
    public byte[]           szRadarIP = new byte[32];

    public NET_IN_RADAR_GETCAPS() {
        this.dwSize = this.size();
    }
}

