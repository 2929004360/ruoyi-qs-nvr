package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 路口数据列表信息
 * @date 2021/09/02
 */
public class NET_CROSS_LIST_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  路口类型
     */
    public int              nCrossType;
    /**
     *  路段数据列表个数
     */
    public int              nRoadListInfoNum;
    /**
     *  路段数据列表
     */
    public NET_SIGNAL_ROAD_LIST_INFO[] stuRoadListInfo = (NET_SIGNAL_ROAD_LIST_INFO[]) new NET_SIGNAL_ROAD_LIST_INFO().toArray(8);
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[32];
}

