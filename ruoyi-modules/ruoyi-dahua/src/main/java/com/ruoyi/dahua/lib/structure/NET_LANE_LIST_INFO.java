package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 路段对应的车道列表
 * @date 2021/09/02
 */
public class NET_LANE_LIST_INFO extends NetSDKLib.SdkStructure {
    /**
     *  车道编号
     */
    public int              nLaneNumber;
    /**
     *  车行灯进口方向车道类型
     */
    public int              nLaneType;
    /**
     *  是否为待行区 0为车道不是待行区，1标示车道有待行区
     */
    public int              nWaitingArea;
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[32];
}

