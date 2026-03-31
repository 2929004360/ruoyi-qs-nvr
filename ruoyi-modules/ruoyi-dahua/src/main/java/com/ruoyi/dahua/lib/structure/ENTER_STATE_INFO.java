package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 进口交通运行状态信息
 * @date 2021/09/01
 */
public class ENTER_STATE_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  进口方向以地理正北方向为起点的顺时针旋转角度，单位为度（°）
     */
    public int              nEnterDir;
    /**
     *  车道数量
     */
    public int              nLaneNumber;
    /**
     *  车道交通运行状态信息个数
     */
    public int              nLaneStateInfoNum;
    /**
     *  车道交通运行状态信息(0~8), 包含1到N（车道数量）个车道交通运行状态信息
     */
    public LANE_STATE_INFO[] stuLaneStateInfo = (LANE_STATE_INFO[]) new LANE_STATE_INFO().toArray(8);
    /**
     *  预留字节 
     */
    public byte[]           byReserved = new byte[128];
}

