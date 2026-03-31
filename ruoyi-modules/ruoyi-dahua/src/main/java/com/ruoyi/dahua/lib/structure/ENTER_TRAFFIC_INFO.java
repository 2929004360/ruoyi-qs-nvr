package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 进口方向交通流信息
 * @date 2021/09/01
 */
public class ENTER_TRAFFIC_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  进口方向以地理正北方向为起点的顺时针旋转角度，单位为度（°）
     */
    public int              nEnterDir;
    /**
     *  进口车道数量
     */
    public int              nEnterLaneNumbner;
    /**
     *  车道交通流信息个数
     */
    public int              nLanTrafficInfoNum;
    /**
     *  车道交通流信息（0~8），包含1到M（进口车道数量）个车道交通流信息
     */
    public LAN_TRAFFIC_INFO[] stuLanTrafficInfo = (LAN_TRAFFIC_INFO[]) new LAN_TRAFFIC_INFO().toArray(8);
    /**
     *  预留字节
     */
    public byte[]           byReserved = new byte[128];
}

