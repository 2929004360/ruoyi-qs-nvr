package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 检测区信息
 * @date 2022/01/07
 */
public class NET_GETSUMMARY_CROWD_STAT_DATA extends NetSDKLib.SdkStructure {
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
     *  区域人数统计列表
     */
    public NET_GETSUMMARY_REGION_PEOPLE_LIST_INFO[] stuRegionPeopleList = (NET_GETSUMMARY_REGION_PEOPLE_LIST_INFO[]) new NET_GETSUMMARY_REGION_PEOPLE_LIST_INFO().toArray(16);
    /**
     *  全局拥挤人群报警个数
     */
    public int              nCrowdEventNum;
    /**
     *  拥挤人群列表
     */
    public NET_GETSUMMARY_CROWD_LIST_INFO[] stuCrowdList = (NET_GETSUMMARY_CROWD_LIST_INFO[]) new NET_GETSUMMARY_CROWD_LIST_INFO().toArray(5);
    /**
     *  报警区域个数
     */
    public int              nRegionEventNum;
    /**
     *  报警区域列表
     */
    public NET_GETSUMMARY_REGION_LIST_INFO[] stuRegionList = (NET_GETSUMMARY_REGION_LIST_INFO[]) new NET_GETSUMMARY_REGION_LIST_INFO().toArray(8);
    /**
     *  保留字节
     */
    public byte[]           szReserved = new byte[1024];
}

