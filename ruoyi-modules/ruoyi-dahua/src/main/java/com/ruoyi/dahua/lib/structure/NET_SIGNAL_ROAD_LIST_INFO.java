package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 路段数据列表信息
 * @date 2021/09/02
 */
public class NET_SIGNAL_ROAD_LIST_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  路段Id
     */
    public int              nRoadSectionId;
    /**
     *  是否有二次过街 0没有，1有二次过街
     */
    public int              nIsTwiceCrossing;
    /**
     *  路段名称
     */
    public byte[]           szRoadSectionName = new byte[32];
    /**
     *  路段方向0 - 北 | 1 - 东北 | 2 - 东 | 3 - 东南 | 4 - 南 | 5 - 西南 | 6 - 西 | 7 - 西北 |
     */
    public int              nRoadSectionDir;
    /**
     *  车行灯进口方向的车道数
     */
    public int              nRoadSectionInLaneNum;
    /**
     *  车行灯出口方向的车道数
     */
    public int              nRoadSectionOutLaneNum;
    /**
     *  该路段对应的车道列表个数
     */
    public int              nLaneListNum;
    /**
     *  该路段对应的车道列表
     */
    public NET_LANE_LIST_INFO[] stuLaneListInfo = (NET_LANE_LIST_INFO[]) new NET_LANE_LIST_INFO().toArray(8);
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[32];
}

