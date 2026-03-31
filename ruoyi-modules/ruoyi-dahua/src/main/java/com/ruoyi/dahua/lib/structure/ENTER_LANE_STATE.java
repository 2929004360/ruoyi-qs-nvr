package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 进口车道功能状态信息
 * @date 2021/09/01
 */
public class ENTER_LANE_STATE extends NetSDKLib.SdkStructure {
	/**
     *  进口方向(0~359)以地理正北方向为起点的顺时针旋转角度，单位为度（°）
     */
    public int              nEnterDir;
    /**
     *  进口车道总数该进口车道总数，包含该进口所有车道
     */
    public int              nLaneNumber;
    /**
     *  车道信息个数
     */
    public int              nLaneInfoNum;
    /**
     *  车道交通运行状态信息(0~8), 包含1到N（车道数量）个车道交通运行状态信息
     */
    public LANE_STATE_INFO_EX[] stuLaneInfo = (LANE_STATE_INFO_EX[]) new LANE_STATE_INFO_EX().toArray(8);
    /**
     *  预留字节
     */
    public byte[]           byReserved = new byte[128];
}

