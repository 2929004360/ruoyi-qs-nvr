package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 检测区统计信息
 * @date 2022/01/07
 */
public class NET_CROWD_STAT_DATA extends NetSDKLib.SdkStructure {
    /**
     *  通道号
     */
    public int              nChannelID;
    /**
     *  检测区内总人数
     */
    public int              nGloabalPeopleNum;
    /**
     *  检测区内区域个数
     */
    public int              nRegionNum;
    /**
     *  检测区内区域人数统计信息
     */
    public NET_REGION_PEOPLE_STAT_INFO[] stuRegionPeople = (NET_REGION_PEOPLE_STAT_INFO[]) new NET_REGION_PEOPLE_STAT_INFO().toArray(8);
    /**
     *  保留字节
     */
    public byte[]           byReserved = new byte[1024];
}

