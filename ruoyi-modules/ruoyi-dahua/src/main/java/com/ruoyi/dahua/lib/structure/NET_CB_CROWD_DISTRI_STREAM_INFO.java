package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 订阅人群分布图实时统回调信息
 * @date 2022/01/07
 */
public class NET_CB_CROWD_DISTRI_STREAM_INFO extends NetSDKLib.SdkStructure {
    /**
     *  检测区个数
     */
    public int              nCrowStatNum;
    /**
     *  检测区统计信息
     */
    public NET_CROWD_STAT_DATA[] stuCrowdStatData = (NET_CROWD_STAT_DATA[]) new NET_CROWD_STAT_DATA().toArray(8);
    /**
     *  保留字节
     */
    public byte[]           byReserved = new byte[2048];
}

