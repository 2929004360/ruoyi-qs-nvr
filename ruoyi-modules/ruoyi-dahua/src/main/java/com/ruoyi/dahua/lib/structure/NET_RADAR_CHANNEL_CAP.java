package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 雷达信道配置能力
 * @date 2022/08/04 10:13:31
 */
public class NET_RADAR_CHANNEL_CAP extends NetSDKLib.SdkStructure {
    /**
     * 是否支持该能力
     */
    public int              bSupport;
    /**
     * 信道号列表的有效数据个数
     */
    public int              nListNum;
    /**
     * 配置时可选的信道号列表
     */
    public int[]            nChannelList = new int[24];
    /**
     * 预留
     */
    public byte[]           byReserved = new byte[224];

    public NET_RADAR_CHANNEL_CAP() {
    }
}

